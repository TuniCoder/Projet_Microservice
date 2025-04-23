package com.esprit.microservice.mini_projet.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue panierQueue() {
        return new Queue("panierQueue", false);
    }
}
