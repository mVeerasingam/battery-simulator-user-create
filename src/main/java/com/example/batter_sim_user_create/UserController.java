package com.example.batter_sim_user_create;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public List<UserAccount> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getUser/{user_id}")
    public UserAccount findUser(@PathVariable Long user_id, @Valid @RequestBody UserAccount userAccount){
        return userService.getUserById(user_id);
    }

    @PostMapping("/create")
    public UserAccount addUser(@Valid @RequestBody UserAccount userAccount){
        System.out.println("Received user: " + userAccount);
        return userService.createUser(userAccount);
    }

    @DeleteMapping("/delete/{user_id}")
    public void deleteUser(@PathVariable Long user_id){
        userService.deleteUser(user_id);
    }
}
