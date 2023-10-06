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

import java.util.UUID;

@Service("AccountRegisterService")
@RequiredArgsConstructor
public class AccountRegisterService extends ServiceImpl<UserMapper, User> implements IAccountRegisterService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthorityMapper authorityMapper;

    @Transactional
    @Override
    public R<Object> register(User user) {
        try {
            if (hasExistUser(user.getUsername()))
                return R.failed("该用户名已经被注册！");

            return saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private boolean hasExistUser(String username) {
        return query().eq("username", username).one() != null;
    }

    private R<Object> saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());

        this.save(user);
        authorityMapper.setAuthority(user.getId(), AuthorityType.USER, user.getUsername());

        return R.success("恭喜你，注册用户成功！");
    }

}
