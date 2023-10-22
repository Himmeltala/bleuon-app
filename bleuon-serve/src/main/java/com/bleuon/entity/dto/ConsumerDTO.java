package com.bleuon.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity.dto
 * @author: zheng
 * @date: 2023/10/7
 */
@Schema(description = "非铭感信息的用户模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerDTO implements Serializable {

    private String id;

    private String username;

    private String phone;

    private String email;

    private String profession;

    private String company;

    private String position;

    private String degree;

    private String avatar;

    private String signature;

    private String sex;

    private Timestamp createDate;

    private Timestamp modifyDate;

}
