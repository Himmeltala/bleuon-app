package com.bleuon.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 与前端交互的实体类，封装 jwt，包括过期时间、用户名、角色、权限等信息。
 *
 * @author zheng
 */
@Data
public class Token implements Serializable {

    private Long expire;
    private String value;
    private String username;
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
