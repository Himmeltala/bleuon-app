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

    @Schema(description = "UUID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @Schema(description = "用户名")
    @Pattern(regexp = ValidPattern.ACCOUNT, message = "错误的手机号或邮箱或用户名的格式！")
    @TableField
    private String username;

    @Pattern(regexp = ValidPattern.PASSWORD, message = "英文、.、数字，长度在8~16")
    @TableField
    private String password;

    @Pattern(regexp = ValidPattern.PHONE, message = "不是合法的手机号!")
    @TableField
    private String phone;

    @Email(message = "不是合法的邮箱地址！")
    @TableField
    private String email;

    @Schema(description = "职业")
    @TableField
    private String profession;

    @Schema(description = "公司")
    @TableField
    private String company;

    @Schema(description = "行业")
    @TableField
    private String position;

    @Schema(description = "学历")
    @TableField
    private String degree;

    @Schema(description = "头像地址")
    @TableField
    private String avatar;

    @Schema(description = "个性签名")
    @TableField
    private String signature;

    @Schema(description = "性别")
    @Pattern(regexp = "(男|女|保密)", message = "性别男、女或保密")
    @TableField
    private String sex;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    public ConsumerModel(String id) {
        this.id = id;
    }

}
