package com.bleuon.auth;

import com.bleuon.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void authenticate(User user) {
        // TODO AuthenticationManager 用户认证
    }

}
