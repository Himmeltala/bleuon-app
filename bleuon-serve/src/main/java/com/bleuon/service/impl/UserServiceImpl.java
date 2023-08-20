package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.mapper.UserMapper;
import com.bleuon.model.LoginUser;
import com.bleuon.model.User;
import com.bleuon.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zheng
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 认真用户，替换默认的 InMemoryUserDetailManager，该实现类实现自 UserDetailsService。
     * 本项目使用数据库查询用户名和密码，得到的结果封装为 UserDetails 对象。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // queryWrapper.eq(e -> e.getUsername(), username);
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);

        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // TODO 查询对应的权限信息

        return new LoginUser(user);
    }

}
