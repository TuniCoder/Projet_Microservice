package tn.esprit.profile_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    @RabbitListener(queues = "userQueue")
    public void receiveUserId(String userId) {
        System.out.println("Received user ID from product service: " + userId);
        // 👉 ici tu peux chercher le rôle de l'utilisateur et faire une autre action
    }
}
