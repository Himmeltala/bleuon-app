package com.bleuon.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 与前端交互的实体类，封装 jwt，包括过期时间、用户名、角色、权限等信息。
 *
 * @author zheng
 */
@Data
public class AuthVo implements Serializable {

    private Long expire;
    private String token;

    public AuthVo() {
    }

    public AuthVo(Long expire, String token) {
        this.expire = expire;
        this.token = token;
    }

}
