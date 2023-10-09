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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

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
    public R<UserDto> findById(@Validated User params) {
        UserDto exists = userService.findById(params.getId());
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该用户！");
    }

    @GetMapping("/find/by/token")
    public R<UserDto> findByToken(@RequestHeader(KeyVals.Token) String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        UserDto exists = userService.findById(uid);
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该用户！");
    }

    @PostMapping("/renewal/by/token")
    public R<Object> renewalByToken(@RequestHeader(KeyVals.Token) String token, @RequestBody @Validated User body) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        body.setId(uid);
        boolean status = userService.renewal(body);

        return status ? R.success("更新资料成功！") : R.failed("更新资料失败！");
    }

    @PostMapping("/renewal/avatar")
    public R<String> renewalAvatar(@RequestHeader(KeyVals.Token) String token,
                                   @RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        User user = new User();
        user.setId(uid);

        String url = userService.renewalAvatar(user, file);

        if (StringUtils.hasText(url)) {
            return R.success("上传头像成功！", url);
        }

        return R.failed("上传头像失败！");
    }

}
