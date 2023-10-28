package com.learn.kafka.controller;

import com.learn.kafka.dto.KafkaMessageDTO;
import com.learn.kafka.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KafkaController {
    private final KafkaConsumerService consumerService;

    @Value("${server.port}")
    String portNumber;

    public KafkaController(KafkaConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping(("/"))
    public String getMessages() {
        String str = "Consumer is Listening on port " + portNumber;
        System.out.println(str);
        return str;
    }
}