package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zheng
 */
@Data
@TableName("t_consumers")
@NoArgsConstructor
@AllArgsConstructor
public class Consumer implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @Pattern(regexp = ValidPattern.USERNAME, message = "字母、中文、-、_，不能以数字开头、-、_开头，长度在4~16")
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

    @TableField
    private String profession;

    @TableField
    private String company;

    @TableField
    private String position;

    @TableField
    private String degree;

    @TableField
    private String avatar;

    @TableField
    private String signature;

    @Pattern(regexp = "(男|女|保密)", message = "性别男、女或保密")
    @TableField
    private String sex;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    public Consumer(String id) {
        this.id = id;
    }

}
