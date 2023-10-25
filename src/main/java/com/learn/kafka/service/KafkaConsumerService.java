package com.learn.kafka.service;

import com.learn.kafka.entity.Message;
import com.learn.kafka.repository.MessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerService {
    private final List<String> receivedMessages = new ArrayList<>();

    private final MessageRepository messageRepository;

    public KafkaConsumerService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void receiveMessage(String message) {
        receivedMessages.add(message);
        // Process the received message
        System.out.println("Received message: " + message);

        //Save the Data in DB
        Message messageEntity = new Message();
        messageEntity.setMessage(message);

        messageRepository.save(messageEntity);

    }

    public List<String> getMessages() {
        return receivedMessages;
    }
}