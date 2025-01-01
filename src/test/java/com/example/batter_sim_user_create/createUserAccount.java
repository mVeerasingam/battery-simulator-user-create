package com.example.batter_sim_user_create;

import org.junit.jupiter.api.Test;

public class createUserAccount {
    @Test
    public void testLombok(){
        UserAccount user = new UserAccount(
                1,
                "TestAccount",
                "Test",
                "Account",
                "TestAccount@Mail.com",
                "Secure1234@."
        );
        System.out.println(user);
    }
}
