package com.example.batter_sim_user_create;

import com.example.batter_sim_user_create.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiValidationService {
    private UserRepository userRepository;

    @Autowired
    public ApiValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidApiKey(UUID userId, String apiKey) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        return user.getApiKey().toString().equals(apiKey);
    }
}
