package com.example.batter_sim_user_create;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserValidationConsumer {

    private UserService userService;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public UserValidationConsumer(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "user_validation_request")
    public void handleUserValidationRequest(String user_id) {
        try {
            UUID userUUID = UUID.fromString(user_id);
            boolean isValid = userService.isValidUser(userUUID);
            String response = isValid ? "Valid" : "Invalid";
            rabbitTemplate.convertAndSend("user_validation_exchange", "user.validation.response", response);
            System.out.println("Sent validation response for user: " + user_id);
        } catch (Exception e) {
            System.err.println("Error during API validation: " + e.getMessage());
        }
    }
}
