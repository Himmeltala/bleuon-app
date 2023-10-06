package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.service.impl.UserService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/6
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/find/one")
    public R<UserDto> findOne(@Validated User vo) {
        return userService.findOne(vo);
    }

}
