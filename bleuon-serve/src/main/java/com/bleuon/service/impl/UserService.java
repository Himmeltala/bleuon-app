package com.bleuon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.IUserService;
import com.bleuon.utils.http.R;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/6
 */
@Service("UserService")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public R<UserDto> findOne(User vo) {
        User user = query().eq("id", vo.getId()).one();
        if (!Objects.isNull(user)) {
            UserDto dto = new UserDto();
            BeanUtil.copyProperties(user, dto);
            return R.success(dto);
        }
        return R.failed("不存在该用户！");
    }

}
