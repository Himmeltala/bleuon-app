package com.bleuon.authentication.filter;

import com.bleuon.mapper.LoginMapper;
import com.bleuon.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户名和密码认证请求。
 * <p>
 * 检验请求头是否携带 Token。如果没有携带 Token 认证失败，放行进入下一个过滤链。
 * 通过 SecurityContextHolder，设置认证信息。
 *
 * @author zheng
 */
@Component
public class AuthJwtFilter extends OncePerRequestFilter {

    @Resource
    private LoginMapper mapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJwt(authorization);

        if (claims != null) {
            String jwtId = claims.getId();
            Long expire = redisTemplate.getExpire(jwtId);

            if (expire != null && expire != -2) {
                List<String> authorities = mapper.queryAuthorities(Map.of("username", claims.get("username")));
                UserDetails details = JwtUtil.toUserDetails(claims, authorities);

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
