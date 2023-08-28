package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
import com.bleuon.service.AccountRegisterService;
import com.bleuon.service.MailRelatedService;
import com.bleuon.service.ResetPasswordService;
import com.bleuon.utils.HttpUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@RequestMappingPrefix("/auth")
public class AuthController {

    @Resource
    private MailRelatedService mailRelatedService;

    @Resource
    private AccountRegisterService registerService;

    @Resource
    private ResetPasswordService resetPasswordService;

    @GetMapping("/aks-mail-verify-code")
    public Vo askMailVerifyCode(@RequestParam @Email(message = "不是一个合法的电子邮箱地址") String email,
                                @RequestParam @Pattern(regexp = "(register|login|reset)", message = "发送验证码的类型是 register 或 login 或 reset") String type,
                                HttpServletRequest http) {
        return mailRelatedService.getMailVerifyCode(email, type, HttpUtil.getIpAddr(http));
    }

    @PostMapping("/verify-mail-code")
    public AuthVo verifyMailCode(@RequestBody User user,
                                 @RequestParam @Pattern(regexp = "(register|login|reset)", message = "发送验证码的类型是 register 或 login 或 reset") String type,
                                 @RequestParam @Pattern(regexp = "^\\d{6}$", message = "验证码必须是 6 个数字") String code) {
        return mailRelatedService.verifyMailCode(user, type, code);
    }

    @PostMapping("/account-register")
    public Vo accountRegister(@RequestBody User user) {
        return registerService.register(user);
    }

    @PostMapping("/reset-password")
    public Vo resetPassword(@RequestBody User user) {
        return resetPasswordService.resetPassword(user);
    }

}
