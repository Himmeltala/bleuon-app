package com.bleuon;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootTest
class BleuonServeApplicationTests {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void sendMail() {
        String encode = passwordEncoder.encode("12345678");
        System.out.println(encode);
    }

}
