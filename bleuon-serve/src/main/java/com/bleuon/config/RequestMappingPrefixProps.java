package com.bleuon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置到 application.yml
 *
 * @author zheng
 */
@Data
@Component
@ConfigurationProperties(prefix = "request.mapping.prefix")
public class RequestMappingPrefixProps {

    String prefix = "api";

}
