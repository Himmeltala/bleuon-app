package com.bleuon.config;

import com.bleuon.exception.LoginFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置类
 *
 * @author zheng
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 配置 api
        http.authorizeHttpRequests(auth -> {
            // 不需要认证的 api
            auth.requestMatchers("/api/public/**").permitAll();
            // 除上的 api 以外的所有 api 都要认证
            auth.anyRequest().authenticated();
        });

        // 登录 api 配置
        http.formLogin(conf -> {
            conf.failureHandler(new LoginFailureHandler());
        });

        // 关闭 csrf 和 cors
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.disable());
        // 前后端分离，关闭 Session
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // 登出 api 配置
        http.logout(conf -> conf.invalidateHttpSession(true));

        return http.build();
    }

}
