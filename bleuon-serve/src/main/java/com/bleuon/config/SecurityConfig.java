package com.bleuon.config;

import com.bleuon.authentication.filter.AuthJwtFilter;
import com.bleuon.authentication.handler.*;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Resource
    private AuthJwtFilter jwtFilter;

    @Resource
    private AuthSuccessHandler authSuccessHandler;

    @Resource
    private AuthFailureHandler authFailureHandler;

    @Resource
    private UnAuthSuccessHandler unAuthSuccessHandler;

    @Resource
    private AuthEntryPointHandler authEntryPointHandler;

    @Resource
    private AuthAccessDeniedHandler authAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            // 不需要认证的 api
            auth.requestMatchers("/api/auth/**").permitAll();
            // 除上之外的所有 api 都需要认证
            auth.anyRequest().authenticated();
        });

        http.formLogin(conf -> conf
                .loginProcessingUrl("/api/auth/login")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
        );

        http.logout(conf -> conf
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(unAuthSuccessHandler)
        );

        // 异常处理器
        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(authEntryPointHandler)
                .accessDeniedHandler(authAccessDeniedHandler)
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        // 关闭 Session 会话管理，取消 JSESSIONID
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
