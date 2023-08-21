package com.bleuon.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.mapper.UserMapper;
import com.bleuon.model.UserDetailsImplModel;
import com.bleuon.model.User;
import com.bleuon.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 替换默认的 InMemoryUserDetailManager，使用 DbUserDetailsServiceImpl。
 *
 * @author zheng
 */
@Service
public class DbUserDetailsServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public DbUserDetailsServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 替换默认的 InMemoryUserDetailManager。
     * 使用数据库根据用户名查询用户信息，得到的结果封装为 UserDetails 对象。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);

        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        return new UserDetailsImplModel(user);
    }

}
