package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.AuthorityType;
import com.bleuon.entity.User;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.IAccountRegisterService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service("AccountRegisterService")
@RequiredArgsConstructor
public class AccountRegisterService extends ServiceImpl<UserMapper, User> implements IAccountRegisterService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityMapper authorityMapper;

    @Transactional
    @Override
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
    @Override
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
            body.setRegisterDate(new Timestamp(new Date().getTime()));
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
