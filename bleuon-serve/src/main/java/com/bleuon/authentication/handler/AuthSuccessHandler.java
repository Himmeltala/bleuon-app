package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.entity.vo.req.AuthVoReq;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.UserDetailsUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 登录成功处理器
 *
 * @author zheng
 */
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        // 登录成功，获取 UserDetails
        // TODO 使用自己封装的 UserDetails
        UserDetails details = (User) authentication.getPrincipal();
        // 传递 UserDetails，创建 jwt 令牌
        String token = JwtUtil.createJwt(details, 1);
        // 创建与前端传输的数据格式 AuthenticationVoReq
        AuthVoReq voReq = new AuthVoReq();
        voReq.setToken(token);
        voReq.setExpire(JwtUtil.getExpire());
        voReq.setUsername(details.getUsername());
        voReq.setAuthorities(UserDetailsUtil.getAuths(details));
        // 返回 ResponseEntity 实体类
        response.getWriter()
                .write(JSON.toJSONString(ResponseEntity.status(200).body(voReq)));
    }

}
