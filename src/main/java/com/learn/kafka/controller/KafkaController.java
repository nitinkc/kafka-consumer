package com.learn.kafka.controller;

import com.learn.kafka.dto.KafkaMessageDTO;
import com.learn.kafka.service.KafkaConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KafkaController {
    private final KafkaConsumerService consumerService;

    public KafkaController(KafkaConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping(("/messages"))
    public List<KafkaMessageDTO> getMessages() {
        List<String> messages = consumerService.getMessages();
        List<KafkaMessageDTO> messageDTOs = new ArrayList<>();
        for (String message : messages) {
            messageDTOs.add(new KafkaMessageDTO(message));
        }
        return messageDTOs;
    }
}