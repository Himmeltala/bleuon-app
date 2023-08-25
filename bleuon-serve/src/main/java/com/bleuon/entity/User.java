package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author zheng
 */
@Data
@TableName("t_users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId(value = "id")
    private String id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "phone")
    private String phone;
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
    @TableField(value = "sex")
    private String sex;
    @TableField(value = "register_date")
    private Timestamp registerDate;

}
