package com.bleuon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @package: com.bleuon.config
 * @author: zheng
 * @date: 2023/10/13
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customize() {
        Components components = new Components();

        return new OpenAPI().
                components(components)
                .info(new Info()
                        .title("SpringDoc API")
                        .description("SpringDoc 接口测试")
                        .version("1.0.0"));
    }

}
