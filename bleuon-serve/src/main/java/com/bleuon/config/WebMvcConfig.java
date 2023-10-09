package com.bleuon.config;

import com.bleuon.annotaion.RequestMappingPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Mvc 配置类
 *
 * @author zheng
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestMappingPrefixProps requestMappingPrefixProps;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                requestMappingPrefixProps.getPrefix(),
                c -> c.isAnnotationPresent(RequestMappingPrefix.class));
    }

}
