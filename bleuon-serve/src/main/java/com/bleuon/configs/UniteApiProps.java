package com.bleuon.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "uniteapi")
public class UniteApiProps {

    String prefix = "api";

}
