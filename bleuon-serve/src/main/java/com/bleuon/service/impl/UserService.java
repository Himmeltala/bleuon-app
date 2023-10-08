package com.bleuon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.IUserService;
import com.bleuon.utils.http.R;
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
@Service("UserService")
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public R<UserDto> findById(User params) {
        User user = query().eq("id", params.getId()).one();
        if (!Objects.isNull(user)) {
            UserDto dto = new UserDto();
            BeanUtil.copyProperties(user, dto);
            return R.success(dto);
        }
        return R.failed("不存在该用户！");
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
