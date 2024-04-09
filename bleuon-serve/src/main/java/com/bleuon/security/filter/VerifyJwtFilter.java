package com.bleuon.security.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.bleuon.constant.Constants;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.service.impl.PermissionService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.IpUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * 用户名和密码认证请求。
 * <p>
 * 检验请求头是否携带 Token。如果没有携带 Token 认证失败，放行进入下一个过滤链。
 * 通过 SecurityContextHolder，设置认证信息。
 *
 * @author zheng
 */
@Component
@RequiredArgsConstructor
public class VerifyJwtFilter extends OncePerRequestFilter implements Serializable {

    private final JwtUtil jwtUtil;
    private final PermissionService permissionService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String backendCookie = IpUtil.parseCookie(request, Constants.BACK_END);
        boolean isBackend = IpUtil.isSameSite(request);
        String jwtVal;

        if (backendCookie == null || !isBackend) {
            jwtVal = request.getHeader(Constants.FRONT_END);
        } else {
            if (backendCookie.startsWith("{") && backendCookie.endsWith("}")) {
                JSONObject parsed = JSON.parseObject(backendCookie);
                jwtVal = "Bearer " + parsed.get("value").toString();
            } else {
                jwtVal = "Bearer " + backendCookie;
            }
        }

        Claims claims = jwtUtil.parseJwt(jwtVal);

        if (claims != null) {
            String id = (String) claims.get("id");
            String type = (String) claims.get("type");
            String username = (String) claims.get("username");

            String jwtId = claims.getId();
            Long expire = redisTemplate.getExpire(jwtId);
            List<String> authorities;

            if (expire != null && expire != -2) {
                if (type.equals(Constants.USER_TYPE_NORMAL)) {
                    authorities = permissionService.findConsumerAuthorityList(id, username);
                } else {
                    authorities = permissionService.findAdminAuthorityList(id, username);
                }

                CustomUserDetails details = new CustomUserDetails(id, username, "******", type, authorities);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }

}
