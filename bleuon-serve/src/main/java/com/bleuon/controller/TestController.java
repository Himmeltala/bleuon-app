package com.bleuon.controller;

import com.bleuon.annotaion.UniteApi;
import org.springframework.web.bind.annotation.PostMapping;

@UniteApi("/test")
public class TestController {

    @PostMapping("/hello")
    public String hello() {
        return "hello world!";
    }

}
