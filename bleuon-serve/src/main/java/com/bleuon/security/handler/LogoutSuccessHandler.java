package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.constant.HttpCode;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        String authToken = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJwt(authToken);

        AuthVo vo = new AuthVo();

        if (claims != null) {
            String jwtUuid = claims.getId();
            Boolean hasKey = redisTemplate.hasKey(jwtUuid);
            if (Boolean.TRUE.equals(hasKey)) {
                redisTemplate.delete(jwtUuid);

                vo.setMessage("退出成功！");
                vo.setCode(HttpCode.SUCCESS);
                response.getWriter()
                        .write(JSON.toJSONString(vo));
            } else {
                vo.setMessage("Token 已经过期或不存在，无法退出！");
                vo.setCode(HttpCode.ERROR);
                response.getWriter()
                        .write(JSON.toJSONString(vo));
            }
        } else {
            vo.setMessage("没有携带 Token，无法退出！");
            vo.setCode(HttpCode.ERROR);
            response.getWriter()
                    .write(JSON.toJSONString(vo));
        }
    }

}
