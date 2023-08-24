package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.Codes;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import com.bleuon.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理器
 *
 * @author zheng
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        UserDetails details = (User) authentication.getPrincipal();
        // 传递 UserDetails，创建 jwt 令牌
        String jwtUuid = UUID.randomUUID().toString();
        Long expire = JwtUtil.getExpire();
        String token = JwtUtil.createJwt(details, jwtUuid, expire);
        // 存入 Redis 数据库
        redisTemplate.opsForValue().set(jwtUuid, token, expire, TimeUnit.SECONDS);

        AuthVoResponse vo = new AuthVoResponse();
        vo.setToken(token);
        vo.setExpire(JwtUtil.getExpire());
        vo.setMessage("登录成功！");
        vo.setCode(Codes.SUCCESS);

        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
