package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.dto.ConsumerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/13
 */
@Schema(description = "用户关注模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectingConsumerModel implements Serializable {

    @Schema(description = "用户关注 UUID")
    @TableId
    private String id;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建日期")
    @TableField("create_date")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "关注的用户实体类")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Schema(description = "关注者的 ID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;

    @Schema(description = "用户模型")
    @TableField(exist = false)
    private ConsumerDTO consumer;

}
