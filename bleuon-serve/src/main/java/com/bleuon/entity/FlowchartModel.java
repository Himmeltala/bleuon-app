package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @Schema(description = "UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @Schema(description = "文件名，即流程图名称")
    @TableField("file_name")
    private String fileName;

    @Schema(description = "流程图元数据")
    @TableField
    private String json;

    @Schema(description = "流程图预览 data uri")
    @TableField("data_uri")
    private String dataUri;

    @Schema(description = "流程图宽度")
    @TableField
    private Double width;

    @Schema(description = "流程图高度")
    @TableField
    private Double height;

    @Schema(description = "流程图背景颜色")
    @TableField("bg_color")
    private String bgColor;

    @Schema(description = "流程图网格大小配置信息")
    @TableField("grid_size")
    private Double gridSize;

    @Schema(description = "流程图网格配置信息")
    @TableField("draw_grid")
    private String drawGrid;

    @Schema(description = "流程图图形连接配置")
    @TableField("connector_default")
    private String connectorDefault;

    @Schema(description = "流程图图形路由配置")
    @TableField("router_default")
    private String routerDefault;

    @Schema(description = "是否公开")
    @TableField("is_public")
    private Integer isPublic;

    @Schema(description = "是否审核通过")
    @TableField("is_legal")
    private Integer isLegal;

    @Schema(description = "是否分享")
    @TableField("is_share")
    private Integer isShare;

    @Schema(description = "是否模板")
    @TableField("is_blueprint")
    private Integer isBlueprint;

    @Schema(description = "创建日期")
    @TableField("create_date")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "分享截止日期")
    @TableField("dead_share_date")
    private Timestamp deadShareDate;

    @Schema(description = "流程图所属用户 ID")
    @TableField("consumer_id")
    private String consumerId;

    @Schema(description = "流程图所属用户模型")
    @TableField(exist = false)
    private ConsumerDTO consumer;

}
