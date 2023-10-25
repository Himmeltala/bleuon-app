package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/13
 */
@Schema(description = "用户关注模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectingConsumerModel implements Serializable {

    @TableId
    private String id;

    private String remark;

    private Timestamp createDate;

    private Timestamp modifyDate;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectorId;

    @TableField(exist = false)
    private ConsumerModel consumer;

}
