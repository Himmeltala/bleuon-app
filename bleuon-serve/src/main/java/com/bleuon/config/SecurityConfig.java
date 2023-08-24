package com.bleuon.config;

import com.bleuon.authentication.filter.AuthJwtLoginFilter;
import com.bleuon.authentication.handler.*;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Resource
    private AuthJwtLoginFilter authJwtLoginFilter;

    @Resource
    private AuthUsernamePasswordSuccessHandler authUsernamePasswordSuccessHandler;

    @Resource
    private AuthUsernamePasswordFailureHandler authUsernamePasswordFailureHandler;

    @Resource
    private UnAuthJwtLogoutSuccessHandler unAuthJwtLogoutSuccessHandler;

    @Resource
    private AuthJwtLoginEntryPointHandler authJwtLoginEntryPointHandler;

    @Resource
    private AuthJwtLoginAccessDeniedHandler authJwtLoginAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            // 不需要认证的 api
            auth.requestMatchers("/api/auth/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll();
            auth.requestMatchers("/api/public/**").permitAll();
            // 除上之外的所有 api 都需要认证
            auth.anyRequest().authenticated();
        });

        http.formLogin(conf -> conf
                .loginProcessingUrl("/api/auth/login")
                .successHandler(authUsernamePasswordSuccessHandler)
                .failureHandler(authUsernamePasswordFailureHandler)
        );

        http.logout(conf -> conf
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(unAuthJwtLogoutSuccessHandler)
        );

        // 异常处理器
        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(authJwtLoginEntryPointHandler)
                .accessDeniedHandler(authJwtLoginAccessDeniedHandler)
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        // 关闭 Session 会话管理，取消 JSESSIONID
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 添加 jwt 校验过滤器链
        http.addFilterBefore(authJwtLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
