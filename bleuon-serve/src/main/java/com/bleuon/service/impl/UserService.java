package com.bleuon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.FileService;
import com.bleuon.service.IUserService;
import com.bleuon.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileService fileService;
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
    public boolean renewal(User user) {
        try {
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            Integer status = mapper.renewal(user);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public String renewalAvatar(User user, MultipartFile file) {
        try {
            String imgUrl = fileService.upload("/static/images/avatar", user.getId(), file);

            if (StringUtils.hasText(imgUrl)) {
                user.setAvatar(imgUrl);
                boolean success = renewal(user);
                if (success) {
                    return imgUrl;
                }
            }

            return "";
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
