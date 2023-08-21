package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.mapper.BleuUserMapper;
import com.bleuon.model.BleuUserDetailsImpl;
import com.bleuon.model.User;
import com.bleuon.service.BleuUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 1. 继承 MybatisPlus 的 ServiceImpl
 * 2. 实现 BleuUserService
 * 3. 实现 UserDetailsService，替换默认的 InMemoryUserDetailManager。重写 loadUserByUsername，自定义认证。
 *
 * @author zheng
 */
@Service
public class BleuUserServiceImpl
        extends ServiceImpl<BleuUserMapper, User>
        implements BleuUserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final BleuUserMapper bleuUserMapper;

    public BleuUserServiceImpl(BleuUserMapper bleuUserMapper, PasswordEncoder passwordEncoder) {
        this.bleuUserMapper = bleuUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 认证用户，替换默认的 InMemoryUserDetailManager，该实现类实现自 UserDetailsService。
     * 本项目使用数据库查询用户名和密码，得到的结果封装为 UserDetails 对象。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = bleuUserMapper.selectOne(queryWrapper);

        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        // TODO 查询对应的权限信息

        return new BleuUserDetailsImpl(user);
    }

}
