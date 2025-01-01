package com.example.batter_sim_user_create;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
}
