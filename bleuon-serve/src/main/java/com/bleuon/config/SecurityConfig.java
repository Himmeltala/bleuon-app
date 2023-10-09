package com.bleuon.config;

import com.bleuon.security.filter.VerifyJwtFilter;
import com.bleuon.security.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final VerifyJwtFilter verifyJwtFilter;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final VerifyJwtEntryPointHandler verifyJwtEntryPointHandler;
    private final VerifyJwtAccessDeniedHandler verifyJwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/entrance/**").permitAll();
            auth.requestMatchers("/api/expose/**").permitAll();
            auth.requestMatchers("/api/file/**").permitAll();
            auth.anyRequest().authenticated();
        });

        http.formLogin(conf -> conf
                .loginProcessingUrl("/api/auth/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
        );

        http.logout(conf -> conf
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
        );

        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(verifyJwtEntryPointHandler)
                .accessDeniedHandler(verifyJwtAccessDeniedHandler)
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(verifyJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
