package com.bleuon.controller;

import com.bleuon.annotaion.UniteApi;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户相关 API
 *
 * @author zheng
 */
@UniteApi("/user")
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
