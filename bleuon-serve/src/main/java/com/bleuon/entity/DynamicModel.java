package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @date: 2023/10/10
 */
@Schema(description = "动态模型")
@TableName("t_dynamics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicModel implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    private String content;

    private Integer digg;

    private Integer bury;

    private Timestamp createDate;

    private Timestamp modifyDate;

    private String consumerId;

}
