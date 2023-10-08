package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.Token;
import com.bleuon.entity.vo.EmailCaptchaVo;
import com.bleuon.service.EmailCaptchaService;
import com.bleuon.service.impl.AccountRegisterService;
import com.bleuon.service.impl.ResetPasswordService;
import com.bleuon.utils.http.IpUtil;
import com.bleuon.utils.http.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description: 获取邮箱验证码等行为，与认证有关的 API
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/8/25
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/auth")
public class AuthorityController {

    private final EmailCaptchaService captchaService;
    private final AccountRegisterService registerService;
    private final ResetPasswordService resetPasswordService;

    @GetMapping("/aks-mail-captcha")
    public R<Object> askMailCaptcha(@Validated EmailCaptchaVo params, HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(params.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");
        String captcha = captchaService.getCaptcha(params.getEmail() + ":captcha");
        boolean status = captchaService.transmit(params.getEmail(), "BleuOn：验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @PostMapping("/verify-mail-captcha")
    public R<Token> verifyMailCode(@RequestBody @Validated EmailCaptchaVo body) {
        String key = body.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！");
        if (captchaService.hasCaptchaEqual(key, body.getCaptcha())) {
            boolean status = captchaService.removeCaptcha(key);
            return status ? R.success("校验成功！") : R.error("校验失败！");
        } else return R.error("验证码错误！");
    }

    @PostMapping("/account-register")
    public R<Object> accountRegister(@RequestBody @Validated User body) {
        return registerService.registerByAccount(body);
    }

    @PostMapping("/email-register")
    public R<Object> emailRegister(@RequestBody @Validated User body) {
        return registerService.registerByEmail(body);
    }

    @PostMapping("/reset-password")
    public R<Object> resetPassword(@RequestBody @Validated User body) {
        return resetPasswordService.resetPassword(body);
    }

}
