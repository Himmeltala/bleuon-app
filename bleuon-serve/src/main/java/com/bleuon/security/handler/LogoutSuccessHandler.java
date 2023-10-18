package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.constant.KeyVals;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登出成功处理器
 *
 * @author zheng
 */
@Component
@RequiredArgsConstructor
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        String authToken = request.getHeader(KeyVals.Token);
        Claims claims = jwtUtil.parseJwt(authToken);

        R<Object> success = R.success("退出成功！");

        if (claims != null) {
            String jwtUuid = claims.getId();
            Boolean hasKey = redisTemplate.hasKey(jwtUuid);
            if (Boolean.TRUE.equals(hasKey)) {
                redisTemplate.delete(jwtUuid);
            }

            response.getWriter()
                    .write(JSON.toJSONString(success));
        } else {
            response.getWriter()
                    .write(JSON.toJSONString(success));
        }
    }

}
