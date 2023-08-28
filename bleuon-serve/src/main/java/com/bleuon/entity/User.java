package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @TableId(value = "id")
    private String id;

    @Pattern(regexp = "^(?![_-])[\\u4e00-\\u9fa5a-zA-Z][\\u4e00-\\u9fa5a-zA-Z0-9_-]{4,16}$", message = "字母、中文、-、_，不能以数字开头、-、_开头，长度在4~16")
    @TableField(value = "username")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9.]{8,16}$", message = "英文、.、数字，长度在8~16")
    @TableField(value = "password")
    private String password;

    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "不是一个合法的手机号码")
    @TableField(value = "phone")
    private String phone;

    @Email(message = "不是一个合法的电子邮箱地址")
    @TableField(value = "email")
    private String email;

    @TableField(value = "profession")
    private String profession;

    @TableField(value = "company")
    private String company;

    @TableField(value = "position")
    private String position;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "signature")
    private String signature;

    @Pattern(regexp = "(男|女|保密)", message = "性别可以是男、女或保密")
    @TableField(value = "sex")
    private String sex;

    @TableField(value = "register_date")
    private Timestamp registerDate;

}
