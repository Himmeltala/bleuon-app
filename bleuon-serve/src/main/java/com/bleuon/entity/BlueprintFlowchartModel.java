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
 * @date: 2023/10/5
 */
@Schema(description = "流程图模板模型")
@TableName("t_blueprint_flowcharts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlueprintFlowchartModel implements Serializable {

    @Schema(description = "UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @Schema(description = "浏览数")
    private Integer views;

    @Schema(description = "导入数")
    private Integer copies;

    @Schema(description = "收藏数")
    private Integer stars;

    @Schema(description = "标签集")
    private String tags;

    @Schema(description = "使用场景")
    private String scene;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "推荐、热门等")
    private String ranking;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "外键，引用流程图表，UUID")
    @TableField("flowchart_id")
    private String flowchartId;

    @Schema(description = "流程图模型")
    @TableField(exist = false)
    private FlowchartModel flowchart;

    @Schema(description = "条件查询时查询所需属性")
    @TableField(exist = false)
    private String fileName;

}
