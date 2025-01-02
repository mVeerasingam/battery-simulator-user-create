package com.example.batter_sim_user_create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserAccount createUser(UserAccount userAccount) {
        if (userRepository.existsByEmail(userAccount.getEmail())) {
            throw new RuntimeException("A user with the email " + userAccount.getEmail() + " already exists.");
        }
        if (userRepository.existsByUserName(userAccount.getUserName())) {
            throw new RuntimeException("A user with the Username " + userAccount.getUserName() + " already exists.");
        }
        System.out.println("Creating User: " + userAccount);
        return  userRepository.save(userAccount);
    }

    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }

    public UserAccount getUserById(UUID user_id){
        return userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("ID " + user_id + " Not Found"));
    }

    public void deleteUser(UUID user_id){
        getUserById(user_id);
        userRepository.deleteById(user_id);
    }
}
