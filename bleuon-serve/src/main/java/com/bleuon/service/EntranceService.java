package com.bleuon.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.AuthorityType;
import com.bleuon.entity.Consumer;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.mapper.ConsumerMapper;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntranceService extends ServiceImpl<ConsumerMapper, Consumer> {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityMapper authorityMapper;

    public R<Object> resetPassword(Consumer consumer) {
        UpdateWrapper<Consumer> updateWrapper = new UpdateWrapper<>();
        String password = passwordEncoder.encode(consumer.getPassword());
        updateWrapper
                .eq("email", consumer.getEmail())
                .set("password", password);
        boolean f = update(consumer, updateWrapper);
        if (f) {
            return R.success("密码重置成功！");
        } else {
            return R.failed("密码重置失败！");
        }
    }

    @Transactional
    public R<Object> registerByAccount(Consumer body) {
        try {
            Consumer exists = query().eq("username", body.getUsername()).one();

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
    public R<Object> registerByEmail(Consumer body) {
        try {
            Consumer exists = query().eq("email", body.getEmail()).one();

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
