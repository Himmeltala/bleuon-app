package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.CodeStatus;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录失败处理器
 *
 * @author zheng
 */
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        AuthVoResponse vo = new AuthVoResponse();
        vo.setMessage("用户名或密码错误！");
        vo.setCode(CodeStatus.PASSWORD_OR_USERNAME_ERROR);
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
