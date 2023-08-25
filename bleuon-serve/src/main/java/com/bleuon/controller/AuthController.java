package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVoR;
import com.bleuon.entity.vo.VoR;
import com.bleuon.service.AccountRegisterService;
import com.bleuon.service.MailLoginService;
import com.bleuon.utils.HttpUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMappingPrefix("/auth")
public class AuthController {

    @Resource
    private MailLoginService mailService;

    @Resource
    private AccountRegisterService registerService;

    @GetMapping("/aks-mail-verify-code")
    public VoR askMailVerifyCode(@RequestParam String mail,
                                 @RequestParam String type, HttpServletRequest http) {
        return mailService.getMailVerifyCode(mail, type, HttpUtil.getIpAddr(http));
    }

    @PostMapping("/verify-mail-code")
    public AuthVoR verifyMailCode(@RequestParam String mail,
                                  @RequestParam String type,
                                  @RequestParam String code) {
        return mailService.verifyMailCode(mail, type, code);
    }

    @PostMapping("/account-register")
    public VoR accountRegister(@RequestBody User user) {
        return registerService.register(user);
    }

}
