package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.utils.http.R;
import com.bleuon.utils.http.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证失败的错误，可能会拦截其他报错。
 *
 * @author zheng
 */
@Slf4j
@Component
public class VerifyJwtEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        log.error(e.getMessage(), e.getCause());

        R<Object> failed = R.build(Status.NO_AUTHORITY.getCode(), "认证失败或服务器错误！");

        response.getWriter()
                .write(JSON.toJSONString(failed));
    }

}
