package com.bleuon.entity.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 与前端交互的实体类，封装 jwt，包括过期时间、用户名、角色、权限等信息。
 *
 * @author zheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthVoReq {

    private Date expire;
    private String token;
    private String username;
    private List<String> roles;
    private List<String> authorities;

}
