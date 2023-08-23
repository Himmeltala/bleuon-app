package com.bleuon.authentication.handler;

import com.alibaba.fastjson2.JSON;
import com.bleuon.consts.CodeStatus;
import com.bleuon.entity.vo.resp.AuthVoResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用于处理在已经通过身份验证的用户尝试访问受限资源时出现的拒绝访问情况。
 * <p>
 * 换句话说，当用户已经登录，但是由于权限不足而无法访问某个特定的页面或资源时，Spring Security 会调用 AccessDeniedHandler 的实现来处理这种情况。
 *
 * @author zheng
 */
@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        AuthVoResponse vo = new AuthVoResponse();
        vo.setCode(CodeStatus.AUTHORITY_NOT_ENOUGH);
        vo.setMessage("权限不足！请联系管理员升级权限。");
        response.getWriter()
                .write(JSON.toJSONString(vo));
    }

}
