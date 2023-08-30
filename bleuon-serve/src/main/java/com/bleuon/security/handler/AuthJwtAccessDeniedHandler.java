package com.bleuon.security.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.constant.HttpCode;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 验证 Token 有效，但是权限不足。
 * <p>
 * 用于处理在已经通过身份验证的用户尝试访问受限资源时出现的拒绝访问情况。
 *
 * @author zheng
 */
@Slf4j
@Component
public class AuthJwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        log.warn("AccessDeniedException [{}: {}]", e.getClass().getName(), e.getMessage());

        Vo vo = AuthVo.error(HttpCode.ERROR, e.getMessage());
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
