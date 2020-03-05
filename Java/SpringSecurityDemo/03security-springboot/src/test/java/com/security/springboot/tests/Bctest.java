package com.security.springboot.tests;

import com.security.springboot.SpringbootApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Bctest extends SpringbootApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testVCrypt() {
        String value = passwordEncoder.encode("secret");
        System.out.println(value);
    }
}
