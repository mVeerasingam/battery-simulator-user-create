package com.example.batter_sim_user_create;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<UserAccount> findByUserName(String userName);
    Optional<UserAccount> findByEmail(String email);
}