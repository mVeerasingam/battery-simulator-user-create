package com.example.batter_sim_user_create;

import com.example.batter_sim_user_create.Exceptions.UserAlreadyExistsException;
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
        // check if fields are unique - if not throws UserAlreadyExistsException (message warning on bad request)
        if (userRepository.existsByEmail(userAccount.getEmail())) {
            throw new UserAlreadyExistsException("A user with the email " + userAccount.getEmail() + " already exists.");
        }
        if (userRepository.existsByUserName(userAccount.getUserName())) {
            throw new UserAlreadyExistsException("A user with the username " + userAccount.getUserName() + " already exists.");
        }

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
