package com.learn.kafka.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.dto.Courses;
import com.learn.kafka.dto.StudentDto;
import com.learn.kafka.entity.StudentData;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Convertor {

    private final ObjectMapper objectMapper;

    public Convertor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T convertJsonToDto(String message,  Class<T> dtoClass) {
        try {
            return objectMapper.readValue(message, dtoClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to StudentDto: " + e.getMessage(), e);
        }
    }

    public StudentData convertStudentDtoToStudentData(StudentDto studentDto) {
        StudentData studentData = new StudentData();
        studentData.setId(studentDto.getId());
        studentData.setDepartment(studentDto.getDepartment());
        studentData.setCourses(studentDto.getCourses().stream()
                .map(Courses::getCourse)
                .collect(Collectors.joining(",")));//"courses":[{"course":"Mathematics"},{"course":"Physics"}] -> "Mathematics, Physics"
        studentData.setFullName(studentDto.getFullName());
        studentData.setStartDate(studentDto.getStartDate());
        return studentData;
    }


}
