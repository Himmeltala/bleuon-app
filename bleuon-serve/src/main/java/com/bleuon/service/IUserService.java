package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/6
 */
public interface IUserService {

    UserDto findById(String id);

    UserDto findByEmail(String email);

    boolean renewal(User user);

    String renewalAvatar(User user, MultipartFile file);
}
