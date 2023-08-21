package com.bleuon.config;

import com.bleuon.exception.LoginFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/user/login").permitAll();
            auth.anyRequest().authenticated();
        });

        http.formLogin(conf -> {
            conf.failureHandler(new LoginFailureHandler());
        });

        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.disable());

        http.logout(conf -> conf.invalidateHttpSession(true));

        return http.build();
    }

}
