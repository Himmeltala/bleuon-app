package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.HttpCode;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
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
 * 用户名和密码登录成功处理器
 *
 * @author zheng
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        UserDetails details = (User) authentication.getPrincipal();

        String jwtUuid = UUID.randomUUID().toString();
        Long expire = JwtUtil.getExpire();
        String token = JwtUtil.createJwt(details, jwtUuid, expire);

        redisTemplate.opsForValue().set(jwtUuid, token, expire, TimeUnit.SECONDS);

        Vo vo = AuthVo.success(HttpCode.SUCCESS, "登录成功！", expire, token);
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
