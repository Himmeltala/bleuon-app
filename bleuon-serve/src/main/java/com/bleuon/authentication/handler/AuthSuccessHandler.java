package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.entity.vo.req.AuthVoReq;
import com.bleuon.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录成功处理器
 *
 * @author zheng
 */
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private List<String> getAuthorities(UserDetails details) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority authority : details.getAuthorities()) {
            list.add(authority.getAuthority());
        }
        return list;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        UserDetails details = (User) authentication.getPrincipal();
        // 传递 UserDetails，创建 jwt 令牌
        String token = JwtUtil.createJwt(details);
        // 创建与前端传输的数据格式 AuthenticationVoReq
        AuthVoReq voReq = new AuthVoReq();
        voReq.setToken(token);
        voReq.setExpire(JwtUtil.getExpire());
        voReq.setUsername(details.getUsername());
        voReq.setAuthorities(getAuthorities(details));
        // 返回 ResponseEntity 实体类
        response.getWriter()
                .write(JSON.toJSONString(ResponseEntity.status(200).body(voReq)));
    }

}
