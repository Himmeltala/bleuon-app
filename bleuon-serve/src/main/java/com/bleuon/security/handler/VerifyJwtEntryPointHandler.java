package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.utils.http.IpUtil;
import com.bleuon.utils.http.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败的错误，可能会拦截其他报错。
 *
 * @author zheng
 */
@Slf4j
@Component
public class VerifyJwtEntryPointHandler implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        boolean sameSite = IpUtil.isSameSite(request);

        if (sameSite) {
            response.sendRedirect("/views/login");
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(R.unpass("认证失败！")));
        }

    }

}
