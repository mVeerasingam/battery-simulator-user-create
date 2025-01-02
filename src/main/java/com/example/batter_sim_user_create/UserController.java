package com.example.batter_sim_user_create;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
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
    public UserAccount findUser(@PathVariable UUID user_id, @Valid @RequestBody UserAccount userAccount){
        return userService.getUserById(user_id);
    }

    @PostMapping("/create")
    public ResponseEntity<UserAccount> createUser(@RequestBody UserAccount user) {
        UserAccount createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{user_id}")
    public void deleteUser(@PathVariable UUID user_id){
        userService.deleteUser(user_id);
    }
}
