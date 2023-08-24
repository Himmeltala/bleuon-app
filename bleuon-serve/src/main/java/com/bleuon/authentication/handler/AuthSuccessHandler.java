package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.Codes;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 登录成功处理器
 *
 * @author zheng
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisUtil redisUtil;

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
        String jwtUuid = UUID.randomUUID().toString();
        Long expire = JwtUtil.getExpire();
        String token = JwtUtil.createJwt(details, jwtUuid, expire);
        // 存入 Redis 数据库
        redisUtil.set(jwtUuid, token, expire);
        // 创建与前端传输的数据格式 AuthenticationVoReq
        AuthVoResponse vo = new AuthVoResponse();
        vo.setToken(token);
        vo.setExpire(JwtUtil.getExpire());
        vo.setMessage("登录成功！");
        vo.setCode(Codes.SUCCESS);
        // 返回 ResponseEntity 实体类
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
