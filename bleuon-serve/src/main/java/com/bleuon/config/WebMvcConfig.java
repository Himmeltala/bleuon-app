package com.bleuon.config;

import com.bleuon.annotaion.UniteApi;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Mvc 配置类
 *
 * @author zheng
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private UniteApiProps uniteApiProps;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                uniteApiProps.getPrefix(),
                c -> c.isAnnotationPresent(UniteApi.class));
    }

}
