package com.bleuon.authentication;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.mapper.UserMapper;
import com.bleuon.entity.User;
import com.bleuon.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 替换默认的 InMemoryUserDetailManager，使用 UserDetailsServiceImpl。
 *
 * @author zheng
 */
@Service
public class UserDetailsServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 替换默认的 InMemoryUserDetailManager。
     * 使用数据库根据用户名查询用户信息，得到的结果封装为 UserDetails 对象。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsernameOrEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRoles().split(","))
                .authorities(user.getAuthorities().split(","))
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
