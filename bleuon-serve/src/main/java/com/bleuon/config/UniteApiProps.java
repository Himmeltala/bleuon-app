package com.bleuon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zheng
 */
@Data
@Component
@ConfigurationProperties(prefix = "uniteapi")
public class UniteApiProps {

    String prefix = "api";

}
