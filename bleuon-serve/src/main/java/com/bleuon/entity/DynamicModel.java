package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @Schema(description = "UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @Schema(description = "动态内容")
    private String content;

    @Schema(description = "赞成数")
    private Integer digg;

    @Schema(description = "反对数")
    private Integer bury;

    @Schema(description = "创建日期")
    @TableField("create_date")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "动态所属用户 ID")
    @TableField("consumer_id")
    private String consumerId;

}
