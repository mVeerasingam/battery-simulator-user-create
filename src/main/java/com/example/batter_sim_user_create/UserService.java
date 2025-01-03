package com.example.batter_sim_user_create;

import com.example.batter_sim_user_create.Exceptions.UserAlreadyExistsException;
import com.example.batter_sim_user_create.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user
    public UserAccount createUser(UserAccount userAccount) {
        // Check if the email or username already exists
        if (userRepository.existsByEmail(userAccount.getEmail())) {
            throw new UserAlreadyExistsException("A user with the email " + userAccount.getEmail() + " already exists.");
        }
        if (userRepository.existsByUserName(userAccount.getUserName())) {
            throw new UserAlreadyExistsException("A user with the username " + userAccount.getUserName() + " already exists.");
        }

        // Generate unique API key
        String apiKey = generateUniqueApiKey();
        userAccount.setApiKey(apiKey);

        return userRepository.save(userAccount);
    }

    // Update an existing user
    public UserAccount updateUser(UUID user_id, UserAccount userDetails) {
        UserAccount existingUser = getUserById(user_id);

        // Update user details (excluding the ID and API key)
        existingUser.setUserName(userDetails.getUserName());
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPassword(userDetails.getPassword());

        return userRepository.save(existingUser);
    }

    // Delete a user
    public void deleteUser(UUID user_id) {
        getUserById(user_id);  // Throws UserNotFoundException if not found
        userRepository.deleteById(user_id);
    }

    // Generate unique apiKey, used for the Simulation API
    // this func is only to be used when creating or updating user
    private String generateUniqueApiKey() {
        String apiKey;
        boolean isUnique;
        do {
            // Generate a random API key
            apiKey = UUID.randomUUID().toString();
            // Check if the API key already exists in the database
            isUnique = !userRepository.existsByApiKey(apiKey);
        } while (!isUnique); // Repeat until a unique key is generated
        return apiKey;
    }

    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }

    public UserAccount getUserById(UUID user_id) {
        return userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + user_id + " not found"));
    }

    public UserAccount getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User with username " + userName + " not found"));
    }
}
