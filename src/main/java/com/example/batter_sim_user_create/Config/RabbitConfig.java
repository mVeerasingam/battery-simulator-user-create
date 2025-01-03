package com.example.batter_sim_user_create.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue apiValidationQueue() {
        return new Queue("api_validation", false);  // Durable = false for temporary queue
    }
}
