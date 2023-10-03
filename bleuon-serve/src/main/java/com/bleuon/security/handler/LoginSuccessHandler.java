package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.dto.Token;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
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
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        String jwtUuid = UUID.randomUUID().toString();
        Long expire = JwtUtil.getExpire();
        String value = JwtUtil.createJwt(details, jwtUuid, expire);

        redisTemplate.opsForValue().set(jwtUuid, value, expire, TimeUnit.SECONDS);

        Token token = new Token();
        token.setValue(value);
        token.setExpire(JwtUtil.getExpire());
        token.setUsername(details.getUsername());
        token.setId(details.getId());

        R<Token> success = R.success("登录成功！", token);
        response.getWriter()
                .write(JSON.toJSONString(success));
    }

}
