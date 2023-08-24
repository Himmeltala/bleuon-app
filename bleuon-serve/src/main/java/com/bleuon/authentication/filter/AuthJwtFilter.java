package com.bleuon.authentication.filter;

import com.bleuon.mapper.AuthMapper;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 请求进入时，这个过滤器第一时间执行，首先检验请求头是否携带 Token。
 * <p>
 * 如果没有携带 Token 认证失败，放行进入下一个过滤链。通过 SecurityContextHolder，设置认证信息，表示认证未通过，执行处理器。
 *
 * @author zheng
 */
@Component
public class AuthJwtFilter extends OncePerRequestFilter {

    @Resource
    private AuthMapper authMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentication = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJwt(authentication);

        if (claims != null) {
            String jwtId = claims.getId();
            Long expire = redisUtil.getExpire(jwtId);
            if (expire != -2) {
                List<String> authorities = authMapper.queryAuthsByUserId(null, (String) claims.get("username"));
                UserDetails details = JwtUtil.toUserDetails(claims, authorities);
                // 创建 UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 设置 SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
