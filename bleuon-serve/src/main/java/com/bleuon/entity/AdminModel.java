package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
public class AdminModel {

    @Schema(description = "UUID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String id;

    private String username;

    private String password;

    private String phone;

    private Timestamp createDate;

    private Timestamp modifyDate;

}
