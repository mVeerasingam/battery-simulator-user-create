package com.example.batter_sim_user_create;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @NotBlank(message = "UserName Cannot Be Blank")
    private String user_name;

    @NotBlank(message = "First Name Cannot Be Blank")
    private String first_name;

    @NotBlank(message = "Last Name Cannot Be Blank")
    private String last_name;

    @Email
    @NotBlank(message = "Email Cannot Be Blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, include 1 uppercase letter, and 1 special character."
    )
    private String password;
}
