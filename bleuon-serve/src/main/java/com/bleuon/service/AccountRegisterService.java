package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.consts.HttpCode;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.Vo;
import com.bleuon.mapper.AuthMapper;
import com.bleuon.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountRegisterService extends ServiceImpl<UserMapper, User> {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private AuthMapper authMapper;

    public Vo register(User user) {
        Vo vo = new Vo();

        if (hasExistUser(user.getUsername())) {
            vo.setMessage("用户名已被注册");
            vo.setCode(HttpCode.ERROR);
        } else {
            saveUserToDb(vo, user);
        }

        return vo;
    }

    private boolean hasExistUser(String username) {
        return query().eq("username", username).one() != null;
    }

    private void saveUserToDb(Vo vo, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());

        boolean saveUserIsOk = this.save(user);
        boolean setAuthIsOk = authMapper.setAuthority(user.getId(), 3L, user.getUsername());

        vo.setCode(saveUserIsOk && setAuthIsOk ? HttpCode.SUCCESS : HttpCode.ERROR);
        vo.setMessage(saveUserIsOk && setAuthIsOk ? "注册成功！" : "注册失败！");
    }

}
