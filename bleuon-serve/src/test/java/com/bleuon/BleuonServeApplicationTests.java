package com.bleuon;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootTest
class BleuonServeApplicationTests {


    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test() {
        String encode = passwordEncoder.encode("12345678");
        System.out.println(encode);
    }

}
