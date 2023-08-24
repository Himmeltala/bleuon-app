package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.vo.AuthVoResponse;
import com.bleuon.service.impl.AuthMailServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMappingPrefix("/auth")
public class AuthController {

    @Resource
    private AuthMailServiceImpl mailService;

    @GetMapping("/aks-mail-verify-code")
    public AuthVoResponse askMailVerifyCode(@RequestParam String mail,
                                            @RequestParam String type, HttpServletRequest http) {
        return mailService.getMailVerifyCode(mail, type, http.getRemoteHost());
    }

    @PostMapping("/verify-mail-code")
    public AuthVoResponse verifyMailCode(@RequestParam String mail,
                                         @RequestParam String type,
                                         @RequestParam String code) {
        return mailService.verifyMailCode(mail, type, code);
    }

}
