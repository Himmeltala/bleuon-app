package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.utils.http.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * Token 有效，但是权限不足。
 * 用于处理在已经通过身份验证的用户尝试访问受限资源时出现的拒绝访问情况。
 *
 * @author zheng
 */
@Slf4j
@Component
public class VerifyJwtAccessDeniedHandler implements AccessDeniedHandler, Serializable {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(R.unpass("权限不足！")));
    }

}
