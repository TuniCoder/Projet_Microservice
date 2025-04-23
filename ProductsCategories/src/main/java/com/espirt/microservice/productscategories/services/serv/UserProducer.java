package com.espirt.microservice.productscategories.services.serv;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendUserId(String userId) {
        rabbitTemplate.convertAndSend("userQueue", userId);
        System.out.println("Sent user ID: " + userId);
    }
}
