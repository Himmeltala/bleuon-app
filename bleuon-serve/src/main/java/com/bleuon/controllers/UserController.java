package com.bleuon.controllers;

import com.bleuon.annotaions.UniteApi;
import com.bleuon.models.User;
import com.bleuon.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin
@UniteApi("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public void login(User user){
        service.login(user);
    }

}
