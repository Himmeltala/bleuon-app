package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/22
 */
@Schema(description = "管理员用户模型")
@TableName("t_admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminModel implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String avatar;

    private Timestamp createDate;

    private Timestamp modifyDate;

    @TableField(exist = false)
    private List<AuthorityModel> authorities;

    @TableField(exist = false)
    private List<RoleModel> roles;

    public AdminModel(String username) {
        this.username = username;
    }

}
