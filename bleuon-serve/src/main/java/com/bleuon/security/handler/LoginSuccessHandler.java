package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.TokenModel;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * 用户名和密码登录成功处理器
 *
 * @author zheng
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler, Serializable {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        R<TokenModel> success = R.success("登录成功！", jwtUtil.grant(details));
        response.getWriter().write(JSON.toJSONString(success));
    }

}
