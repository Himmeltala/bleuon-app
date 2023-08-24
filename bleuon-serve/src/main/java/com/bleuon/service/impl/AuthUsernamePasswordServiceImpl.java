package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.mapper.AuthUsernamePasswordMapper;
import com.bleuon.mapper.UserBaseMapper;
import com.bleuon.service.AuthUsernamePasswordService;
import com.bleuon.entity.User;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户名密码登录 Service 实现类。
 * <p>
 * 当用户选择用户名、邮箱、手机号 + 密码的方式登录时处理。
 * <p>
 * 同时也是替换 InMemoryUserDetailManager，即通过数据库查询用户信息，得到的结果封装为 UserDetails 对象。
 *
 * @author zheng
 */
@Service
public class AuthUsernamePasswordServiceImpl extends ServiceImpl<UserBaseMapper, User> implements AuthUsernamePasswordService, UserDetailsService {

    @Resource
    private AuthUsernamePasswordMapper authUsernamePasswordMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsernameOrEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        List<String> auths = authUsernamePasswordMapper.queryAuthorities(null, user.getUsername());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(auths.toArray(new String[0]))
                .build();
    }

    private User findUserByUsernameOrEmail(String text) {
        return query()
                .eq("username", text)
                .or()
                .eq("email", text)
                .or()
                .eq("phone", text)
                .one();
    }

}
