package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.Token;
import com.bleuon.entity.dto.UserDto;
import com.bleuon.entity.vo.EmailCaptchaVo;
import com.bleuon.service.EmailCaptchaService;
import com.bleuon.service.EntranceService;
import com.bleuon.service.TokenService;
import com.bleuon.service.impl.UserService;
import com.bleuon.utils.http.IpUtil;
import com.bleuon.utils.http.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @description: 入口接口
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/8/25
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/entrance")
public class EntranceController {

    private final UserService userService;
    private final TokenService tokenService;
    private final EntranceService entranceService;
    private final EmailCaptchaService captchaService;

    /**
     * 获取邮箱验证码
     */
    @GetMapping("/aks/email-captcha")
    public R<Object> askMailCaptcha(@Validated EmailCaptchaVo params,
                                    HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(params.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");
        String captcha = captchaService.getCaptcha(params.getEmail() + ":captcha");
        boolean status = captchaService.transmit(params.getEmail(), "BleuOn：验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    /**
     * 获取邮箱登录验证码
     */
    @GetMapping("/ask/login-email-captcha")
    public R<Object> askLoginMailCaptcha(@Validated EmailCaptchaVo params,
                                         HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(params.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        UserDto exists = userService.findByEmail(params.getEmail());
        if (Objects.isNull(exists)) {
            return R.error("邮箱不存在！");
        }

        String captcha = captchaService.getCaptcha(params.getEmail() + ":captcha");
        boolean status = captchaService.transmit(params.getEmail(), "BleuOn：登录验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    /**
     * 获取邮箱注册验证码
     */
    @GetMapping("/ask/register-email-captcha")
    public R<Object> askRegisterMailCaptcha(@Validated EmailCaptchaVo params,
                                            HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(params.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        UserDto exists = userService.findByEmail(params.getEmail());
        if (!Objects.isNull(exists)) {
            return R.error("邮箱被注册！");
        }

        String captcha = captchaService.getCaptcha(params.getEmail() + ":captcha");
        boolean status = captchaService.transmit(params.getEmail(), "BleuOn：注册验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    /**
     * 获取密码重置的邮箱验证码
     */
    @GetMapping("/ask/reset-email-captcha")
    public R<Object> askResetMailCaptcha(@Validated EmailCaptchaVo params,
                                         HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(params.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        UserDto exists = userService.findByEmail(params.getEmail());
        if (Objects.isNull(exists)) {
            return R.error("邮箱没有注册！");
        }

        String captcha = captchaService.getCaptcha(params.getEmail() + ":captcha");
        boolean status = captchaService.transmit(params.getEmail(), "BleuOn：密码重置验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    /**
     * 校验验证码
     */
    @PostMapping("/verify/email-captcha")
    public R<Token> verifyMailCode(@RequestBody @Validated EmailCaptchaVo body) {
        String key = body.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！");
        if (captchaService.hasCaptchaEqual(key, body.getCaptcha())) {
            captchaService.removeCaptcha(key);
            return R.success("校验成功！");
        } else return R.error("验证码错误！");
    }

    /**
     * 校验邮箱注册验证码
     */
    @PostMapping("/verify/register-email-captcha")
    public R<Object> verifyRegisterMailCaptcha(@RequestBody @Validated User body,
                                               @Validated EmailCaptchaVo params) {
        String key = body.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！", null);

        if (captchaService.hasCaptchaEqual(key, params.getCaptcha())) {
            captchaService.removeCaptcha(key);
            return entranceService.registerByEmail(body);
        } else return R.error("验证码错误！", null);
    }

    /**
     * 校验邮箱登录验证码
     */
    @PostMapping("/verify/login-email-captcha")
    public R<Token> verifyLoginMailCaptcha(@RequestBody @Validated EmailCaptchaVo body) {
        String key = body.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！", null);

        if (captchaService.hasCaptchaEqual(key, body.getCaptcha())) {
            captchaService.removeCaptcha(key);

            UserDto exists = userService.findByEmail(body.getEmail());
            if (Objects.isNull(exists)) {
                return R.error("该用户不存在！");
            } else {
                Token token = tokenService.grant(new CustomUserDetails(exists.getId(),
                        exists.getUsername(),
                        "******", new ArrayList<>()));
                return R.success("登录成功！", token);
            }
        } else return R.error("验证码错误！", null);
    }

    /**
     * 邮箱、用户名或手机号注册
     */
    @PostMapping("/account-register")
    public R<Object> accountRegister(@RequestBody @Validated User body) {
        return entranceService.registerByAccount(body);
    }

    @PostMapping("/reset-password")
    public R<Object> resetPassword(@RequestBody @Validated User body) {
        return entranceService.resetPassword(body);
    }

}
