package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/6
 */
public interface IUserService {

    UserDto findById(String id);

    UserDto findByEmail(String email);

    boolean renewal(User vo);

}
