package com.esprit.microservice.mini_projet.Service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String requestProductJsonById(String productId) {
        System.out.println("Sending product ID to panier: " + productId);
        return (String) rabbitTemplate.convertSendAndReceive("panierQueue", productId);
    }
}
