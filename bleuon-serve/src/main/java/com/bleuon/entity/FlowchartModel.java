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
 * @date: 2023/9/29
 */
@Schema(description = "流程图模型")
@TableName("t_flowcharts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowchartModel implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    private String filename;

    @Schema(description = "流程图元数据")
    private String json;

    private String dataUri;

    private Double width;

    private Double height;

    private String bgColor;

    private Double gridSize;

    private String drawGrid;

    private String connectorDefault;

    private String routerDefault;

    private Integer isPublic;

    private Integer isLegal;

    private Integer isShare;

    private Integer isBlueprint;

    private Timestamp createDate;

    private Timestamp modifyDate;

    private Timestamp deadShareDate;

    private String consumerId;

    @TableField(exist = false)
    private ConsumerModel consumer;

}
