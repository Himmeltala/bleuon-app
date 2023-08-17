package com.bleuon.services;

import com.bleuon.mappers.UserMapper;
import com.bleuon.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper mapper;

    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public void login(User user) {

    }

}
