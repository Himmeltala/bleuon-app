package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.HttpCode;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
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
public class AuthJwtEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        log.warn("AuthenticationException [{}: {}]", e.getClass().getName(), e.getMessage());

        Vo vo = AuthVo.error(HttpCode.ERROR, e.getMessage());
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
