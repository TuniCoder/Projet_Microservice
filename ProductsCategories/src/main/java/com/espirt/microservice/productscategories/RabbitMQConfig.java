package com.espirt.microservice.productscategories;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "profileQueue";

    @Bean
    public Queue profileQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}