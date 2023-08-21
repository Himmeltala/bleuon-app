package com.bleuon.controller;

import com.bleuon.annotaion.UniteApi;
import com.bleuon.model.User;
import com.bleuon.service.BleuLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@UniteApi
public class LoginController {

    private final BleuLoginService service;

    public LoginController(BleuLoginService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        service.login(user);
        return "hello";
    }

}
