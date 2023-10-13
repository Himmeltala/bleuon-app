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

    @Bean
    public OpenAPI customize() {
        Components components = new Components();

        SecurityScheme scheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ImVhMjA5ZmJiLThmMGUtNDgzZS1iZTg2LWMzNjI5ZWNiZTZkMSIsInVzZXJuYW1lIjoiSGltbWVsYmxldSIsInN1YiI6ImJsZXVvbiIsImV4cCI6MTY5NzczNTk2NiwiaWF0IjoxNjk3MTMxMTY2LCJqdGkiOiJmNmI1MDQyOC1kNDNkLTQ2MzUtYjYzNy01MzY5YTAyNWRiMmIifQ.C8uCQ2lyys4zRcPFhSMTN4bkN7xvtIcLIy-U0tJbXho");

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
