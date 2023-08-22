package com.bleuon.authentication.filter;

import com.bleuon.utils.JwtUtil;
import io.jsonwebtoken.Claims;
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

@Component
public class AuthJwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头的 token
        String authentication = request.getHeader("Authorization");
        // 解析 token
        Claims claims = JwtUtil.parseJwt(authentication);
        if (claims != null) {
            // 封装 UserDetails
            UserDetails details = JwtUtil.toUserDetails(claims);
            // 创建 UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 设置 SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

}
