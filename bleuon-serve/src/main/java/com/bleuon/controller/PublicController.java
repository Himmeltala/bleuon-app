package com.bleuon.controller;

import com.bleuon.annotaion.UniteApi;
import com.bleuon.model.User;
import com.bleuon.auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@UniteApi("/public")
public class PublicController {

    private final AuthService authService;

    public PublicController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        authService.authenticate(user);
        return "hello";
    }

}
