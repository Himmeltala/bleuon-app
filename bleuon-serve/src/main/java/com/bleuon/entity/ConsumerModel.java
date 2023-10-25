package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
 * @date: 2023/8/22
 */
@TableName("t_consumers")
@Schema(description = "用户模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerModel implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @Pattern(regexp = ValidPattern.ACCOUNT, message = "错误的手机号或邮箱或用户名的格式！")
    private String username;

    @Pattern(regexp = ValidPattern.PASSWORD, message = "英文、.、数字，长度在8~16")
    private String password;

    @Pattern(regexp = ValidPattern.PHONE, message = "不是合法的手机号!")
    private String phone;

    @Email(message = "不是合法的邮箱地址！")
    private String email;

    @Schema(description = "职业")
    private String profession;

    @Schema(description = "公司")
    private String company;

    @Schema(description = "行业")
    private String position;

    private String degree;

    private String avatar;

    private String signature;

    @Pattern(regexp = "(男|女|保密)", message = "性别男、女或保密")
    private String sex;

    private Timestamp createDate;

    private Timestamp modifyDate;

    @TableField(exist = false)
    private List<AuthorityModel> authorities;

    @TableField(exist = false)
    private List<RoleModel> roles;

    public ConsumerModel(String id) {
        this.id = id;
    }

}
