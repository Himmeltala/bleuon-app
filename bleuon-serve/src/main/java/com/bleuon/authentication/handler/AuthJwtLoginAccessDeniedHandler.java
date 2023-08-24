package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.Codes;
import com.bleuon.entity.vo.AuthVoResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@Component
public class AuthJwtLoginAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        AuthVoResponse vo = new AuthVoResponse();
        vo.setCode(Codes.AUTH_NOT_ENOUGH);
        vo.setMessage("权限不足！请联系管理员升级权限。");
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
