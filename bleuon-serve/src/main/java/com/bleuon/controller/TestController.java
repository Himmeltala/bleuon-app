package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMappingPrefix("/test")
public class TestController {

    @PostMapping("/hello")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public String hello() {
        return "Hello World! You Have any authorities!";
    }

}
