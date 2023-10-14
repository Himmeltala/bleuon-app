package com.bleuon.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "jwt")
@Data
public class Token implements Serializable {

    @Schema(description = "过期时间")
    private Long expire;

    @Schema(description = "jwt 值")
    private String value;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户 UUID")
    private String id;

    public Token() {
    }

    public Token(Long expire, String value) {
        this.expire = expire;
        this.value = value;
    }

    public Token(Long expire, String value, String username, String id) {
        this.expire = expire;
        this.value = value;
        this.username = username;
        this.id = id;
    }

}
