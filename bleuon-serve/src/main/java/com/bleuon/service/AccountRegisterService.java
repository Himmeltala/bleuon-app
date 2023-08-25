package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.consts.Codes;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.VoR;
import com.bleuon.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountRegisterService extends ServiceImpl<UserMapper, User> {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    public VoR register(User user) {
        VoR vo = new VoR();
        if (hasExistUser(user.getUsername())) {
            vo.setMessage("用户名已被注册");
            vo.setCode(Codes.USER_EXISTS);
        } else {
            saveUserToDb(vo, user);
        }
        return vo;
    }

    private boolean hasExistUser(String username) {
        User user = query().eq("username", username).one();
        return user != null;
    }

    private void saveUserToDb(VoR vo, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());
        try {
            boolean isOk = this.save(user);
            if (isOk) {
                vo.setMessage("注册成功！");
                vo.setCode(Codes.SUCCESS);
            } else {
                vo.setMessage("注册失败！");
                vo.setCode(Codes.ERROR);
            }
        } catch (Exception e) {
            vo.setMessage("注册失败！");
            vo.setCode(Codes.ERROR);
        }
    }

}
