package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.User;
import com.bleuon.entity.dto.Token;
import com.bleuon.service.impl.AccountRegisterService;
import com.bleuon.service.impl.MailRelatedService;
import com.bleuon.service.impl.ResetPasswordService;
import com.bleuon.utils.http.IpUtil;
import com.bleuon.utils.http.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 获取邮箱验证码等行为，与认证有关的 API
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/8/25
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/auth")
public class AuthorityController {

    private final MailRelatedService mailRelatedService;
    private final AccountRegisterService registerService;
    private final ResetPasswordService resetPasswordService;

    @GetMapping("/aks-mail-verify-code")
    public R<Object> askMailVerifyCode(
            @Validated
            @Email(message = "不是一个合法的电子邮箱地址")
            @RequestParam
            String email,

            @Validated
            @Pattern(regexp = ValidPattern.MAIL_CODE_TYPE, message = "发送验证码的类型是 register 或 login 或 reset！")
            @RequestParam
            String type,

            HttpServletRequest http
    ) {
        return mailRelatedService.getMailVerifyCode(email, type, IpUtil.getIp(http));
    }

    @PostMapping("/verify-mail-code")
    public R<Token> verifyMailCode(
            @Validated
            @RequestBody
            User user,

            @Validated
            @Pattern(regexp = ValidPattern.MAIL_CODE_TYPE, message = "发送验证码的类型是 register 或 login 或 reset！")
            @RequestParam
            String type,

            @Validated
            @Pattern(regexp = ValidPattern.DIGIT_6, message = "验证码必须是 6 位正整数！")
            @RequestParam
            String code
    ) {
        return mailRelatedService.verifyMailCode(user, type, code);
    }

    @PostMapping("/account-register")
    public R accountRegister(@RequestBody @Validated User user) {
        return registerService.register(user);
    }

    @PostMapping("/reset-password")
    public R resetPassword(@RequestBody @Validated User user) {
        return resetPasswordService.resetPassword(user);
    }

}
