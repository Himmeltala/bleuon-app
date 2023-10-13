package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.dto.ConsumerDto;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "关注用户实体类")
public class CollectingConsumer implements Serializable {

    @TableId
    private String id;
    private String remark;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "关注的用户实体类")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Schema(description = "关注者的 ID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;

    @TableField(exist = false)
    private ConsumerDto consumer;

    public CollectingConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

}
