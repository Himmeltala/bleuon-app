package com.bleuon.config;

import com.bleuon.authentication.filter.AuthJwtFilter;
import com.bleuon.authentication.handler.AuthFailureHandler;
import com.bleuon.authentication.handler.AuthSuccessHandler;
import com.bleuon.authentication.handler.UnAuthSuccessHandler;
import com.bleuon.entity.vo.req.AuthVoReq;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.UserDetailsUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * Spring Security 配置类
 *
 * @author zheng
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private AuthJwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 配置 api
        http.authorizeHttpRequests(auth -> {
            // 不需要认证的 api
            auth.requestMatchers("/api/auth/**").permitAll();
            // 除上的 api 以外的所有 api 都要认证
            auth.anyRequest().authenticated();
        });

        // 登录 api 配置
        http.formLogin(conf -> conf
                .loginProcessingUrl("/api/auth/login")
                .successHandler(new AuthSuccessHandler())
                .failureHandler(new AuthFailureHandler())
        );

        // 登出 api 配置
        http.logout(conf -> conf
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(new UnAuthSuccessHandler())
        );

        // 关闭 csrf 和 cors
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        // 前后端分离，关闭 Session
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
