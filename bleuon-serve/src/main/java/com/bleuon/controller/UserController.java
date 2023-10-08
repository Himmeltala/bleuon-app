package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.service.impl.UserService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

    @GetMapping("/find/by/id")
    public R<UserDto> findOne(@Validated User vo) {
        return userService.findById(vo);
    }

    @GetMapping("/find/by/token")
    public R<UserDto> findByToken(@RequestHeader(KeyVals.Token) String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        User user = new User();
        user.setId(uid);
        return userService.findById(user);
    }

    @PostMapping("/renewal/by/token")
    public R<Object> renewalByToken(@RequestHeader(KeyVals.Token) String token, @Validated @RequestBody User vo) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        vo.setId(uid);
        boolean status = userService.renewal(vo);

        return status ? R.success("更新资料成功！") : R.failed("更新资料失败！");
    }

}
