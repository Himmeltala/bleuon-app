package com.bleuon.authentication.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 登出成功处理器
 *
 * @author zheng
 */
public class UnAuthSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO 获取请求头的 JWT，获取 JWT 的 ID
        // TODO 删除 Redis 中存储的 JWT
        response.getWriter().write("退出成功！");
    }

}
