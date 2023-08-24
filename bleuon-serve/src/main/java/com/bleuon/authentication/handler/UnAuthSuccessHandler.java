package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.Codes;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登出成功处理器
 *
 * @author zheng
 */
@Component
public class UnAuthSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        String authToken = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJwt(authToken);

        AuthVoResponse vo = new AuthVoResponse();

        if (claims != null) {
            String jwtUuid = claims.getId();
            if (redisUtil.hasKey(jwtUuid)) {
                redisUtil.del(jwtUuid);
                vo.setMessage("退出成功！");
                response.getWriter()
                        .write(JSON.toJSONString(vo));
            } else {
                vo.setMessage("Token 已经过期或不存在，无法退出！");
                vo.setCode(Codes.TOKEN_NONE_OR_EXPIRE);
                response.getWriter()
                        .write(JSON.toJSONString(vo));
            }
        } else {
            vo.setMessage("没有携带 Token，无法退出！");
            vo.setCode(Codes.TOKEN_NONE_OR_EXPIRE);
            response.getWriter()
                    .write(JSON.toJSONString(vo));
        }
    }

}
