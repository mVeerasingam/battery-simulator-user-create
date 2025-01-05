package com.example.batter_sim_user_create.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue userValidationRequestQueue() {
        return new Queue("user_validation_request", true);
    }

    @Bean
    public Queue userValidationResponseQueue() {
        return new Queue("user_validation_response", true);
    }

    @Bean
    public Exchange userValidationExchange() {
        return new DirectExchange("user_validation_exchange", true, false);
    }
}
