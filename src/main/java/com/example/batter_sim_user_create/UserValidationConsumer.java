package com.example.batter_sim_user_create;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserValidationConsumer {

    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "user_validation_request")
    public void handleUserValidationRequest(UUID user_id) {
        try {
            boolean isValid = userService.isValidUser(user_id);
            String response = isValid ? "Valid" : "Invalid";
            rabbitTemplate.convertAndSend("user_validation_exchange", "user.validation.response", response);
        } catch (Exception e) {
            System.err.println("Error during API validation: " + e.getMessage());
        }
    }


    @RabbitListener(queues = "user_validation_response")
    public void handleUserValidationResponse(String response) {
        System.out.println("Received validation response: " + response);
        // Process response i.e. if valid send back to simulation manager it can simulate and vise versa
    }
}
