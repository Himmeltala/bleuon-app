package com.bleuon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

    private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ImVhMjA5ZmJiLThmMGUtNDgzZS1iZTg2LWMzNjI5ZWNiZTZkMSIsInVzZXJuYW1lIjoiSGltbWVsYmxldSIsInN1YiI6ImJsZXVvbiIsImV4cCI6MTY5Nzg3NzMyMywiaWF0IjoxNjk3MjcyNTIzLCJqdGkiOiJhYzIxYzI2Mi1kMDBiLTQ1NjEtYTMwYS1jNzA1NzUwYTJkOGMifQ.DwRS5xWP-0PS73TYxPzC3-pXYMIHLQ_KGYYtnrtzAQ8";

    @Bean
    public OpenAPI customize() {
        Components components = new Components();

        SecurityScheme scheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT").description(token);

        components.addSecuritySchemes("bearerAuth", scheme);

        return new OpenAPI().
                components(components)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("SpringDoc API")
                        .description("SpringDoc 接口测试")
                        .version("1.0.0"));
    }

}
