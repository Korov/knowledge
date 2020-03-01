package com.rolemanager.user.passencoder;

import com.rolemanager.user.ApplicationTests;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestEncoder extends ApplicationTests {
    @Test
    public void test1() {
        String password = BCrypt.hashpw("admin", BCrypt.gensalt());
        System.out.printf("Password is: %s;\n", password);
        String password1 = BCrypt.hashpw("admin", BCrypt.gensalt());
        System.out.printf("Password is: %s;\n", password1);

        boolean result1 = BCrypt.checkpw("admin", password);
        boolean result2 = BCrypt.checkpw("admin", password1);
        System.out.printf("%s\n%s\n", result1, result2);
    }
}
