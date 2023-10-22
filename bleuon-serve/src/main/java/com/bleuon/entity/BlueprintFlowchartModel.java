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

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    private Integer views;

    @Schema(description = "导入数")
    private Integer copies;

    private Integer stars;

    @Schema(description = "标签集")
    private String tags;

    @Schema(description = "使用场景")
    private String scene;

    private String price;

    private String description;

    @Schema(description = "推荐、热门等")
    private String ranking;

    private Timestamp createDate;

    private Timestamp modifyDate;

    private String flowchartId;

    @TableField(exist = false)
    private FlowchartModel flowchart;

    @TableField(exist = false)
    private String filename;

}
