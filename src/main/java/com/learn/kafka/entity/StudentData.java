package com.learn.kafka.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "student_data")
@Data
@RequiredArgsConstructor
public class StudentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String id;
    private String fullName;
    private String department;
    private String courses;
    private Timestamp startDate;
}
