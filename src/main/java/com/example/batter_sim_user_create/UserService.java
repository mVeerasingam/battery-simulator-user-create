package com.example.batter_sim_user_create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserAccount createUser(UserAccount userAccount) {
        System.out.println("Creating User: " + userAccount);
        return  userRepository.save(userAccount);
    }

    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }

    public UserAccount getUserById(Long user_id){
        return userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("ID " + user_id + " Not Found"));
    }

    public void deleteUser(Long user_id){
        getUserById(user_id);
        userRepository.deleteById(user_id);
    }
}
