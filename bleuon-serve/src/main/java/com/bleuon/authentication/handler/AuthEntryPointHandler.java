package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.CodeStatus;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理在需要进行身份验证的请求中，当用户未经身份验证或者身份验证失败时的行为。
 * <p>
 * 当请求没有携带 Token 时，会进入这个处理器进行处理。
 *
 * @author zheng
 */
@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        AuthVoResponse vo = new AuthVoResponse();

        vo.setMessage("Token 过期或不存在");
        vo.setCode(CodeStatus.TOKEN_NONE_OR_EXPIRE);

        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
