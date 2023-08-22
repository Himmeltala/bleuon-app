package com.bleuon.controller;

import com.bleuon.annotaion.UniteApi;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

@UniteApi("/test")
public class TestController {

    @PostMapping("/hello")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public String hello() {
        return "Hello World! You Have any authorities!";
    }

}
