package com.bleuon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/6
 */
@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(String id) {
        User exists = query().eq("id", id).one();
        if (Objects.isNull(exists)) return null;

        UserDto dto = new UserDto();
        BeanUtil.copyProperties(exists, dto);
        return dto;
    }

    public UserDto findByEmail(String email) {
        User exists = query().eq("email", email).one();
        if (Objects.isNull(exists)) return null;

        UserDto dto = new UserDto();
        BeanUtil.copyProperties(exists, dto);
        return dto;
    }

    @Transactional
    @Override
    public boolean renewal(User body) {
        try {
            if (body.getPassword() != null) {
                body.setPassword(passwordEncoder.encode(body.getPassword()));
            }
            Integer status = mapper.renewal(body);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
