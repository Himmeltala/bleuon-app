package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.utils.http.R;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/6
 */
public interface IUserService {

    R<UserDto> findOne(User vo);

}
