package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.service.impl.UserService;
import com.bleuon.utils.FileUploadUtil;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    private final FileUploadUtil fileUploadUtil;

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

    @PostMapping("/upload/avatar/file")
    public R<Object> uploadAvatarFile(@RequestBody MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            boolean success = fileUploadUtil.writeToResources("\\static\\images\\avatars", fileName, inputStream);
            if (success) {
                return R.success("头像上传成功！", "http://localhost:8080/static/images/avatars/" + fileName);
            }
            return R.error("头像上传失败！");
        } catch (IOException e) {
            return R.error("头像上传失败！");
        }
    }

}
