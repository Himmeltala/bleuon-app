package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.Codes;
import com.bleuon.entity.vo.AuthVoR;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证过程中 Token 失效、不存在、或解析错误，或连接 Redis、MySQL 失败的错误。
 *
 * @author zheng
 */
@Component
public class AuthJwtEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        AuthVoR vo = new AuthVoR();

        vo.setMessage("认证被拒绝，具体问题联系管理员。");
        vo.setCode(Codes.AUTHORITY_UNKNOWN_ERROR);

        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
