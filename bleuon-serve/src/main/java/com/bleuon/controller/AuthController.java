package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
import com.bleuon.service.AccountRegisterService;
import com.bleuon.service.MailRelatedService;
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
    private MailRelatedService mailRelatedService;

    @Resource
    private AccountRegisterService registerService;

    @GetMapping("/aks-mail-verify-code")
    public Vo askMailVerifyCode(@RequestParam String mail,
                                @RequestParam String type,
                                HttpServletRequest http) {
        return mailRelatedService.getMailVerifyCode(mail, type, HttpUtil.getIpAddr(http));
    }

    @PostMapping("/verify-mail-code")
    public AuthVo verifyMailCode(@RequestBody User user,
                                 @RequestParam String type,
                                 @RequestParam String code) {
        return mailRelatedService.verifyMailCode(user, type, code);
    }

    @PostMapping("/account-register")
    public Vo accountRegister(@RequestBody User user) {
        return registerService.register(user);
    }

}
