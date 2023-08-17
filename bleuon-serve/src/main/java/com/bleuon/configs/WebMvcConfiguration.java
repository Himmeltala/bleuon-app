package com.bleuon.configs;

import com.bleuon.annotaions.UniteApi;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private UniteApiProps uniteApiProps;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                uniteApiProps.getPrefix(),
                c -> c.isAnnotationPresent(UniteApi.class));
    }

}
