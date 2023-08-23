package com.bleuon.entity.vo.resp;

import com.bleuon.entity.vo.VoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 与前端交互的实体类，封装 jwt，包括过期时间、用户名、角色、权限等信息。
 *
 * @author zheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthVoResponse extends VoResponse {

    // 过期时间
    private Long expire;
    // Token
    private String token;
    // 用户名
    private String username;
    // 权限
    private List<String> authorities;

}
