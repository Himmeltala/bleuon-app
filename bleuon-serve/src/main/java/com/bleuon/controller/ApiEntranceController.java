package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.AuthorityType;
import com.bleuon.constant.Constants;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.TokenModel;
import com.bleuon.entity.vo.EmailCaptchaVO;
import com.bleuon.service.IAdminService;
import com.bleuon.service.IConsumerService;
import com.bleuon.service.IPermissionService;
import com.bleuon.utils.EmailUtil;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.IpUtil;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/8/25
 */
@Tag(name = "登录注册")
@RequiredArgsConstructor
@RequestMappingPrefix("/public/entrance")
public class ApiEntranceController implements Serializable {

    private final IAdminService adminService;
    private final IConsumerService consumerService;
    private final IPermissionService permissionService;
    private final JwtUtil jwtUtil;
    private final EmailUtil emailUtil;

    @Operation(summary = "获取邮箱验证码，没有其他逻辑，输入邮箱直接获取验证码")
    @GetMapping("/aks/email-captcha")
    public R<Object> askMailCaptcha(@Validated EmailCaptchaVO vo,
                                    HttpServletRequest http) {
        boolean duplicate = emailUtil.memorize(vo.getEmail(), IpUtil.getIp(http));

        if (duplicate) {
            return R.error("60s 内只能发送一次！");
        }

        String captcha = emailUtil.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = emailUtil.transmit(vo.getEmail(), "BleuOn：验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @Operation(summary = "获取登录邮箱验证码，在此之前判断邮箱是否存在")
    @GetMapping("/ask/login-email-captcha")
    public R<Object> askLoginMailCaptcha(@Validated EmailCaptchaVO vo,
                                         HttpServletRequest http) {
        boolean duplicate = emailUtil.memorize(vo.getEmail(), IpUtil.getIp(http));

        if (duplicate) {
            return R.error("60s 内只能发送一次！");
        }

        ConsumerModel consumer = new ConsumerModel();
        consumer.setEmail(vo.getEmail());
        ConsumerModel exists = consumerService.findBy(consumer);

        if (Objects.isNull(exists)) {
            return R.error("邮箱不存在！");
        }

        String captcha = emailUtil.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = emailUtil.transmit(vo.getEmail(), "BleuOn：登录验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }


    @Operation(summary = "获取注册邮箱验证码，在此之前判断邮箱是否被注册")
    @GetMapping("/ask/register-email-captcha")
    public R<Object> askRegisterMailCaptcha(@Validated EmailCaptchaVO vo,
                                            HttpServletRequest http) {
        boolean duplicate = emailUtil.memorize(vo.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        ConsumerModel consumer = new ConsumerModel();
        consumer.setEmail(vo.getEmail());
        ConsumerModel exists = consumerService.findBy(consumer);

        if (!Objects.isNull(exists)) {
            return R.error("邮箱被注册！");
        }

        String captcha = emailUtil.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = emailUtil.transmit(vo.getEmail(), "BleuOn：注册验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @Operation(summary = "获取重置密码邮箱验证码，在此之前判断邮箱是否注册或存在")
    @GetMapping("/ask/reset-email-captcha")
    public R<Object> askResetMailCaptcha(@Validated EmailCaptchaVO vo,
                                         HttpServletRequest http) {
        boolean duplicate = emailUtil.memorize(vo.getEmail(), IpUtil.getIp(http));

        if (duplicate) {
            return R.error("60s 内只能发送一次！");
        }

        ConsumerModel consumer = new ConsumerModel();
        consumer.setEmail(vo.getEmail());
        ConsumerModel exists = consumerService.findBy(consumer);

        if (Objects.isNull(exists)) {
            return R.error("邮箱没有注册！");
        }

        String captcha = emailUtil.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = emailUtil.transmit(vo.getEmail(), "BleuOn：密码重置验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @Operation(summary = "校验邮箱验证码，需判断是否过期或存在")
    @PostMapping("/verify/email-captcha")
    public R<TokenModel> verifyMailCode(@RequestBody @Validated EmailCaptchaVO vo) {
        String key = vo.getEmail() + ":captcha";
        boolean expired = emailUtil.hasCaptchaExpire(key);

        if (expired) {
            return R.error("验证码过期或不存在！");
        }

        if (!emailUtil.hasCaptchaEqual(key, vo.getCaptcha())) {
            return R.error("验证码错误！");
        }

        emailUtil.removeCaptcha(key);
        return R.success("校验成功！");
    }


    @Operation(summary = "校验注册验证码")
    @PostMapping("/verify/register-email-captcha")
    public R<Object> verifyRegisterMailCaptcha(@RequestBody @Validated ConsumerModel model,
                                               @Validated EmailCaptchaVO vo) {
        String key = model.getEmail() + ":captcha";
        boolean expired = emailUtil.hasCaptchaExpire(key);

        if (expired) {
            return R.error("验证码过期或不存在！");
        }

        if (!emailUtil.hasCaptchaEqual(key, vo.getCaptcha())) {
            return R.error("验证码错误！");
        }

        emailUtil.removeCaptcha(key);
        ConsumerModel consumer = new ConsumerModel();
        consumer.setEmail(vo.getEmail());
        ConsumerModel exists = consumerService.findBy(consumer);

        if (!Objects.isNull(exists)) {
            return R.error("邮箱已被注册！");
        }

        String uuid = UUID.randomUUID().toString();
        model.setId(uuid);
        model.setUsername("用户_" + uuid);
        boolean status = consumerService.add(model);

        if (!status) {
            return R.error("注册失败！");
        }

        boolean success = permissionService.addConsumerAuthority(model.getId(), AuthorityType.USER, model.getUsername());
        return success ? R.success("注册成功！") : R.error("注册失败！");
    }

    @Operation(summary = "校验登录验证码")
    @PostMapping("/verify/login-email-captcha")
    public R<TokenModel> verifyLoginMailCaptcha(@RequestBody @Validated EmailCaptchaVO vo) {
        String key = vo.getEmail() + ":captcha";
        boolean expired = emailUtil.hasCaptchaExpire(key);

        if (expired) {
            return R.error("验证码过期或不存在！");
        }

        if (!emailUtil.hasCaptchaEqual(key, vo.getCaptcha())) {
            return R.error("验证码错误！");
        }

        emailUtil.removeCaptcha(key);
        ConsumerModel consumer = new ConsumerModel();
        consumer.setEmail(vo.getEmail());
        ConsumerModel exists = consumerService.findBy(consumer);

        if (Objects.isNull(exists)) {
            return R.error("该用户不存在！");
        }

        CustomUserDetails details = new CustomUserDetails(exists.getId(), exists.getUsername(), "******", Constants.USER_TYPE_NORMAL, new ArrayList<>());
        TokenModel token = jwtUtil.grant(details);
        return R.success("登录成功！", token);
    }

    @Operation(summary = "用户名称注册")
    @PostMapping("/account-register")
    public R<Object> accountRegister(@RequestBody @Validated ConsumerModel model) {
        ConsumerModel exists = consumerService.findBy(model);

        if (!Objects.isNull(exists)) {
            return R.error("用户名已被注册！");
        }

        model.setId(UUID.randomUUID().toString());
        boolean status = consumerService.add(model);

        if (!status) {
            return R.error("注册失败！");
        }

        boolean success = permissionService.addConsumerAuthority(model.getId(), AuthorityType.USER, model.getUsername());
        return success ? R.success("注册成功！") : R.error("注册失败！");
    }

    @Operation(summary = "重置用户密码")
    @PostMapping("/reset-password")
    public R<Object> resetPassword(@RequestBody @Validated ConsumerModel model) {
        boolean success = consumerService.upgrade(model);

        if (!success) {
            return R.failed("密码重置失败！");
        }

        return R.success("密码重置成功！");
    }

    @Operation(summary = "管理员登录", description = "后台管理系统的登录入口")
    @PostMapping("/admin-login")
    public R<TokenModel> adminLogin(@RequestBody @Validated AdminModel model) {
        AdminModel exists = adminService.findByUsernameOrPhoneOrEmailAndPwd(model);

        if (Objects.isNull(exists)) {
            return R.error("用户名或密码错误！");
        }

        CustomUserDetails details = new CustomUserDetails(exists.getId(), exists.getUsername(), "******", Constants.USER_TYPE_ADMIN, new ArrayList<>());
        TokenModel token = jwtUtil.grant(details);
        return R.success("登录成功！", token);
    }

    @Operation(summary = "前台用户名、密码、手机号登录", description = "前台系统的登录入口")
    @PostMapping("/account-login")
    public R<TokenModel> accountLogin(@RequestBody @Validated ConsumerModel model) {
        ConsumerModel exists = consumerService.findByUsernameOrPhoneOrEmailAndPwd(model);

        if (Objects.isNull(exists)) {
            return R.error("用户名或密码错误！");
        }

        CustomUserDetails details = new CustomUserDetails(exists.getId(), exists.getUsername(), "******", Constants.USER_TYPE_NORMAL, new ArrayList<>());
        TokenModel token = jwtUtil.grant(details);
        return R.success("登录成功！", token);
    }

}
