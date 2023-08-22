package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.mapper.AuthMapper;
import com.bleuon.mapper.AuthUserDetailsMapper;
import com.bleuon.service.AuthUserDetailsService;
import com.bleuon.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 当用户登录时进入这个类，通过用户名查询用户信息，封装 UserDetails。
 * <p>
 * 替换 InMemoryUserDetailManager。使用数据库根据用户名查询用户信息，得到的结果封装为 UserDetails 对象。
 *
 * @author zheng
 */
@Service
public class AuthUserDetailsServiceImpl extends ServiceImpl<AuthUserDetailsMapper, User> implements AuthUserDetailsService, UserDetailsService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthUserDetailsServiceImpl(AuthMapper authMapper, PasswordEncoder passwordEncoder) {
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsernameOrEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        List<String> auths = authMapper.queryAuthsByUserId(null, username);

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(auths.toArray(new String[0]))
                .build();
    }

    private User findUserByUsernameOrEmail(String text) {
        return query()
                .eq("username", text)
                .or()
                .eq("email", text)
                .one();
    }

}
