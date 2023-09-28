package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.User;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.IResetPasswordService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("ResetPasswordService")
@RequiredArgsConstructor
public class ResetPasswordService extends ServiceImpl<UserMapper, User> implements IResetPasswordService {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public R<Void> resetPassword(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        String password = passwordEncoder.encode(user.getPassword());
        updateWrapper
                .eq("email", user.getEmail())
                .set("password", password);
        boolean f = update(user, updateWrapper);
        if (f) {
            return R.success("密码重置成功！");
        } else {
            return R.failed("密码重置失败！");
        }
    }

}
