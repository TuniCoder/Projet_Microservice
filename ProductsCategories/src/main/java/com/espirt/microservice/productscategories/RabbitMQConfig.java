package com.espirt.microservice.productscategories;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userQueue() {
        return new Queue("userQueue", false);
    }

    @Bean
    public Queue panierQueue() {
        return new Queue("panierQueue", false);
    }
}

