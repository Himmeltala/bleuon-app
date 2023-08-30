package com.bleuon.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.HttpCode;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.Vo;
import com.bleuon.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService extends ServiceImpl<UserMapper, User> {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    public Vo resetPassword(User user) {
        Vo vo = new Vo();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        String password = passwordEncoder.encode(user.getPassword());
        updateWrapper.eq("email", user.getEmail()).set("password", password);
        boolean update = update(updateWrapper);
        if (update) {
            vo.setCode(HttpCode.SUCCESS);
            vo.setMessage("密码重置成功！");
        } else {
            vo.setCode(HttpCode.ERROR);
            vo.setMessage("密码重置失败！");
        }
        return vo;
    }

}
