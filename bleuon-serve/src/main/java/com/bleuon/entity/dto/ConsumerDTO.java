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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户实体类，屏蔽了铭感信息")
public class ConsumerDTO implements Serializable {

    @Schema(
            description = "UUID",
            example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1"
    )
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
