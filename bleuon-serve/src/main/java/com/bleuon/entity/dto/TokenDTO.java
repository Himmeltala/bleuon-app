package com.bleuon.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "token 模型")
@Data
public class TokenDTO implements Serializable {

    @Schema(description = "过期时间")
    private Long expire;

    @Schema(description = "jwt")
    private String value;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户 UUID")
    private String id;

    public TokenDTO() {
    }

    public TokenDTO(Long expire, String value) {
        this.expire = expire;
        this.value = value;
    }

    public TokenDTO(Long expire, String value, String username, String id) {
        this.expire = expire;
        this.value = value;
        this.username = username;
        this.id = id;
    }

}
