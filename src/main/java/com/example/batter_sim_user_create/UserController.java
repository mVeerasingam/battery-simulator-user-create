package com.example.batter_sim_user_create;

import com.example.batter_sim_user_create.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/users")  // Group all routes under /users for a cleaner API
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        List<UserAccount> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable UUID user_id) {
        UserAccount user = userService.getUserById(user_id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<UserAccount> getUserByUserName(@PathVariable String userName) {
        UserAccount user = userService.getUserByUserName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserAccount> createUser(@Valid @RequestBody UserAccount user) {
        UserAccount createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable UUID user_id, @Valid @RequestBody UserAccount userDetails) {
        UserAccount updatedUser = userService.updateUser(user_id, userDetails);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID user_id) {
        userService.deleteUser(user_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content on successful delete
    }
}
