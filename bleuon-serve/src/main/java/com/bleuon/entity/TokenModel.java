package com.bleuon.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "token 模型")
@Data
public class TokenModel implements Serializable {

    @Schema(description = "过期时间")
    private Long expire;

    @Schema(description = "jwt")
    private String value;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户 UUID")
    private String id;

    @Schema(description = "用户类型，管理员或普通用户")
    private String type;

    public TokenModel(Long expire, String value, String username, String id, String type) {
        this.expire = expire;
        this.value = value;
        this.username = username;
        this.id = id;
        this.type = type;
    }

}
