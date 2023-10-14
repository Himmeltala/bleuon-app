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

    @Schema(description = "UUID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "职业")
    private String profession;

    @Schema(description = "公司")
    private String company;

    @Schema(description = "行业")
    private String position;

    @Schema(description = "学历")
    private String degree;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "个性签名")
    private String signature;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "创建日期")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    private Timestamp modifyDate;

}
