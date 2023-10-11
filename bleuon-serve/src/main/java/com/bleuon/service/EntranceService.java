package com.bleuon.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.AuthorityType;
import com.bleuon.entity.User;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.mapper.UserMapper;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntranceService extends ServiceImpl<UserMapper, User> {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityMapper authorityMapper;

    public R<Object> resetPassword(User user) {
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

    @Transactional
    public R<Object> registerByAccount(User body) {
        try {
            User exists = query().eq("username", body.getUsername()).one();

            if (!Objects.isNull(exists)) {
                return R.error("用户名已被注册！");
            }

            body.setPassword(passwordEncoder.encode(body.getPassword()));
            body.setId(UUID.randomUUID().toString());

            boolean status = this.save(body);
            if (status) {
                authorityMapper.setAuthority(body.getId(), AuthorityType.USER, body.getUsername());
                return R.success("注册成功！");
            } else {
                return R.error("注册失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public R<Object> registerByEmail(User body) {
        try {
            User exists = query().eq("email", body.getEmail()).one();

            if (!Objects.isNull(exists)) {
                return R.error("邮箱已被注册！");
            }

            String uuid = UUID.randomUUID().toString();

            body.setId(uuid);
            body.setUsername("用户_" + uuid);
            body.setPassword(passwordEncoder.encode(body.getPassword()));
            boolean status = this.save(body);

            if (status) {
                authorityMapper.setAuthority(body.getId(), AuthorityType.USER, body.getUsername());
                return R.success("注册成功！");
            } else {
                return R.error("注册失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
