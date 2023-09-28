package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidRegexp;
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
@TableName("t_users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @TableId
    private String id;

    @Pattern(regexp = ValidRegexp.USERNAME, message = "字母、中文、-、_，不能以数字开头、-、_开头，长度在4~16")
    @TableField
    private String username;

    @Pattern(regexp = ValidRegexp.PASSWORD, message = "英文、.、数字，长度在8~16")
    @TableField
    private String password;

    @Pattern(regexp = ValidRegexp.PHONE, message = "不是一个合法的手机号码")
    @TableField
    private String phone;

    @Email(message = "不是一个合法的电子邮箱地址")
    @TableField
    private String email;

    @TableField
    private String profession;

    @TableField
    private String company;

    @TableField
    private String position;

    @TableField
    private String avatar;

    @TableField
    private String signature;

    @Pattern(regexp = "(男|女|保密)", message = "性别可以是男、女或保密")
    @TableField
    private String sex;

    @TableField("register_date")
    private Timestamp registerDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

}
