package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.AuthorityType;
import com.bleuon.entity.User;
import com.bleuon.mapper.AuthMapper;
import com.bleuon.mapper.UserMapper;
import com.bleuon.utils.http.R;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountRegisterService extends ServiceImpl<UserMapper, User> {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private AuthMapper authMapper;

    private boolean hasExistUser(String username) {
        return query().eq("username", username).one() != null;
    }

    @Transactional
    public R<Void> register(User user) {
        try {
            if (hasExistUser(user.getUsername()))
                return R.failed("该用户名已经被注册！");

            return saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private R<Void> saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());

        this.save(user);
        authMapper.setAuthority(user.getId(), AuthorityType.USER, user.getUsername());

        return R.success("恭喜你，注册用户成功！");
    }

}
