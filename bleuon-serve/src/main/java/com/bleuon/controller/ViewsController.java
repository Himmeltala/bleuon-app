package com.bleuon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 视图控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/22
 */
@Controller
@RequestMapping("/views")
public class ViewsController {

    @RequestMapping({"/login", "/", ""})
    public String login() {
        return "login";
    }

}
