package com.bleuon.entity.vo;

import com.bleuon.constant.HttpCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 与前端交互的实体类，封装 jwt，包括过期时间、用户名、角色、权限等信息。
 *
 * @author zheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthVo extends Vo implements Serializable {

    private Long expire;
    private String token;

    public AuthVo() {

    }

    public AuthVo(HttpCode code, String message, Object data, Long expire, String token) {
        super(code, message, data);
        this.expire = expire;
        this.token = token;
    }

    public AuthVo(HttpCode code, String message, Long expire, String token) {
        super(code, message);
        this.expire = expire;
        this.token = token;
    }

    public AuthVo(HttpCode code, String message, Object data) {
        super(code, message, data);
    }

    public AuthVo(HttpCode code, String message) {
        super(code, message);
    }

    public static Vo success(HttpCode code, String message, Long expire, String token) {
        return new AuthVo(code, message, expire, token);
    }

}
