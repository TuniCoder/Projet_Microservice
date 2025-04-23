package com.espirt.microservice.productscategories.services.serv;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "profileQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // Process the message here
    }
}