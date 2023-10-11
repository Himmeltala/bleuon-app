package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.Dynamic;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.entity.vo.DynamicCriteria;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.service.impl.DynamicService;
import com.bleuon.service.impl.UserService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
    private final DynamicService dynamicService;

    @GetMapping("/find/by/id")
    public R<UserDto> findById(@RequestHeader(KeyVals.Token) String token,
                               @Validated
                               @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
                               @RequestParam(required = false)
                               String id) {
        UserDto exists;
        if (!StringUtils.hasText(id)) {
            Claims claims = JwtUtil.parseJwt(token);
            exists = userService.findById((String) claims.get("id"));
        } else {
            exists = userService.findById(id);
        }
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该用户！");
    }

    @PostMapping("/upgrade")
    public R<Object> upgrade(@RequestHeader(KeyVals.Token) String token, @RequestBody @Validated User body) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        body.setId(uid);
        boolean status = userService.upgrade(body);

        return status ? R.success("更新资料成功！") : R.error("更新资料失败！");
    }

    @PostMapping("/upgrade/avatar")
    public R<String> upgradeAvatar(@RequestHeader(KeyVals.Token) String token,
                                   @RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        User user = new User();
        user.setId(uid);

        String imgUrl = userService.upgradeAvatar(user, file);

        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传头像成功！", imgUrl);
        }

        return R.error("上传头像失败！");
    }

    @PostMapping("/find/all/dynamic/by/criteria")
    public R<List<Dynamic>> findAllDynamic(@RequestBody DynamicCriteria criteria) {
        List<Dynamic> list = dynamicService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @PostMapping("/upgrade/dynamic")
    public R<Object> upgradeDynamic(@RequestBody Dynamic body) {
        boolean status = dynamicService.upgrade(body);
        return status ? R.success("更新成功！") : R.error("更新失败！");
    }

    @DeleteMapping("/delete/dynamic")
    public R<Object> deleteDynamicById(Dynamic params) {
        boolean status = dynamicService.deleteById(params);
        return status ? R.success("删除成功！") : R.error("删除失败！");
    }

    @PostMapping("/add/dynamic")
    public R<Object> addDynamic(@RequestHeader(KeyVals.Token) String token, @RequestBody Dynamic body) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        body.setUserId(uid);
        boolean status = dynamicService.add(body);
        return status ? R.success("添加成功！") : R.error("添加失败！");
    }

}
