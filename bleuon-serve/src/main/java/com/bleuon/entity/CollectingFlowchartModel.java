package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @date: 2023/10/5
 */
@Schema(description = "收藏流程图模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectingFlowchartModel implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectorId;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String flowchartId;

    private Timestamp createDate;

    public CollectingFlowchartModel(String collectorId, String flowchartId) {
        this.collectorId = collectorId;
        this.flowchartId = flowchartId;
    }

}
