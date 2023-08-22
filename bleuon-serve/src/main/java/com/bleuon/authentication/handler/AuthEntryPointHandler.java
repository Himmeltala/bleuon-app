package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 处理在需要进行身份验证的请求中，当用户未经身份验证或者身份验证失败时的行为。
 * <p>
 * 当请求头不携带 Token 时，会进入这个处理器进行处理。
 *
 * @author zheng
 */
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter()
                .write(JSON.toJSONString(ResponseEntity.status(401).body("未授权，导致认证失败！请先登录。")));
    }

}
