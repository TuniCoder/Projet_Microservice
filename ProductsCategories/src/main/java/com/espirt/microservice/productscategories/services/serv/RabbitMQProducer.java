package com.espirt.microservice.productscategories.services.serv;

import com.espirt.microservice.productscategories.entity.Products;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper; // à injecter dans la config Spring

    private final String queueName = "profileQueue";

    public void sendMessage(Products products) {
        try {
            String message = objectMapper.writeValueAsString(products);
            rabbitTemplate.convertAndSend(queueName, message);
            System.out.println("Sent: " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
