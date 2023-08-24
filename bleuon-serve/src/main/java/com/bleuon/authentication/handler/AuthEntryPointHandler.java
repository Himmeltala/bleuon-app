package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.Codes;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理在需要进行身份验证的请求中，当用户未经身份验证或者身份验证失败时的行为。
 *
 * @author zheng
 */
@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        AuthVoResponse vo = new AuthVoResponse();

        vo.setMessage("可能是身份认证错误，可能是 Token 的解析错误，或者是连接 Redis、MySQL 失败的错误");
        vo.setCode(Codes.AUTHORITY_UNKNOWN_ERROR);

        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
