package com.example.batter_sim_user_create;

import com.example.batter_sim_user_create.Exceptions.UserAlreadyExistsException;
import com.example.batter_sim_user_create.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserAccount user;

    // Valid instance of the user
    @BeforeEach
    public void setUp() {
        user = new UserAccount(
                UUID.randomUUID(),
                "TestAccount",
                "Test",
                "Account",
                "TestAccount@Mail.com",
                "Secure1234@!"
        );
    }

    // Test Creating Account
    @Test
    public void testUserCreate() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByUserName(user.getUserName())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);  // This mock should return 'user'

        UserAccount createdUser = userService.createUser(user);

        assertNotNull(createdUser);  // This will fail if 'createdUser' is null
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getUserName(), createdUser.getUserName());

        // Verify that the save method was called
        verify(userRepository, times(1)).save(user);
    }

    // Test for Duplicate Accounts (Email already exists)
    @Test
    public void testDuplicateUserCreateByEmail() {
        // Mock the behavior of the repository when trying to create a duplicate user by email
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserAlreadyExistsException thrownException = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.createUser(user),
                "Expected createUser() to throw, but it didn't"
        );

        // Verify the exception message
        assertEquals("A user with the email TestAccount@Mail.com already exists.", thrownException.getMessage());

        // Verify that the save method was never called
        verify(userRepository, never()).save(user);
    }

    // Test for Duplicate Accounts (Username already exists)
    @Test
    public void testDuplicateUserCreateByUsername() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByUserName(user.getUserName())).thenReturn(true);

        // Call the service method and expect an exception to be thrown
        UserAlreadyExistsException thrownException = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.createUser(user),
                "Expected createUser() to throw, but it didn't"
        );

        assertEquals("A user with the username TestAccount already exists.", thrownException.getMessage());

        verify(userRepository, never()).save(user);
    }

    // Test for User retrieval by ID (valid)
    @Test
    public void testGetUserByIdValid() {
        UUID userId = user.getUser_id();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserAccount retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(user.getUser_id(), retrievedUser.getUser_id());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
    }

    // Test for User retrieval by ID (invalid)
    @Test
    public void testGetUserByIdInvalid() {
        UUID invalidUserId = UUID.randomUUID();
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        UserNotFoundException thrownException = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(invalidUserId),
                "Expected getUserById() to throw, but it didn't"
        );

        assertEquals("User with ID " + invalidUserId + " not found", thrownException.getMessage());
    }
}
