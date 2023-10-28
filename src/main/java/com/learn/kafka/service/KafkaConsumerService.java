package com.learn.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.converters.Convertor;
import com.learn.kafka.dto.StudentDto;
import com.learn.kafka.entity.Message;
import com.learn.kafka.entity.StudentData;
import com.learn.kafka.repository.MessageRepository;
import com.learn.kafka.repository.StudentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerService {
    private final List<String> receivedMessages = new ArrayList<>();
    private final ObjectMapper objectMapper;
    private final Convertor convertor;
    private final MessageRepository messageRepository;
    private final StudentRepository studentRepository;

    public KafkaConsumerService(ObjectMapper objectMapper, Convertor convertor, MessageRepository messageRepository, StudentRepository studentRepository) {
        this.objectMapper = objectMapper;
        this.convertor = convertor;
        this.messageRepository = messageRepository;
        this.studentRepository = studentRepository;
    }

    @KafkaListener(topics = "${com.topic.simple}", groupId = "${com.group}")
    public void receiveMessage(String message) {
        receivedMessages.add(message);
        // Process the received message
        System.out.println("Received message: " + message);

        //Save the Data in DB
        Message messageEntity = new Message();
        messageEntity.setMessage(message);

        messageRepository.save(messageEntity);
    }

    @KafkaListener(topics = "${com.topic.student}", groupId = "${com.group}")
    public int handleStudentMessage(String message) {
        // Process the received message
        System.out.println("Received message: " + message);

        // Convert JSON string to StudentDto object
        StudentDto studentDto = convertor.convertJsonToDto(message, StudentDto.class);

        // Convert StudentDto to StudentData
        StudentData studentData = convertor.convertStudentDtoToStudentData(studentDto);

        // Save the data in the database
        StudentData savedStudent = studentRepository.save(studentData);

        int idx = savedStudent.getIdx();
        System.out.println("Save the Data with Index : " + idx);

        return idx;
    }
}