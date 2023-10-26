package com.learn.kafka.repository;

import com.learn.kafka.entity.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentData, Integer> {
}
