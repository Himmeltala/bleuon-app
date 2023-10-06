package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.User;
import com.bleuon.mapper.UserMapper;
import com.bleuon.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/6
 */
@Service("UserService")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
}
