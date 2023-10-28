package com.bleuon.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/24
 */
@Schema(description = "角色与权限关联模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityRoleModel implements Serializable {

    private List<AuthorityModel> authorities;

    private RoleModel role;

}
