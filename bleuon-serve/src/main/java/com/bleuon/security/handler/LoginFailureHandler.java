package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.constant.HttpCode;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用户名和密码登录失败处理器
 *
 * @author zheng
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        Vo vo = AuthVo.error(HttpCode.ERROR, "用户名或密码错误！");
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
