package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidRegexp;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/9/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_flowcharts")
public class Flowchart {

    @TableId
    @Max(40)
    private String id;

    @TableField("file_name")
    @Max(30)
    private String fileName;

    @TableField
    private String json;

    @TableField("data_uri")
    private String dataUri;

    @TableField
    private Double width;

    @TableField
    private Double height;

    @TableField("bg_color")
    private String bgColor;

    @TableField("grid_size")
    private Double gridSize;

    @TableField("draw_grid")
    @Pattern(regexp = ValidRegexp.JSON, message = "网格配置 JSON 格式错误！")
    private String drawGrid;

    @TableField("connector_default")
    @Pattern(regexp = ValidRegexp.JSON, message = "connector JSON 数据格式错误！")
    private String connectorDefault;

    @TableField("router_default")
    @Pattern(regexp = ValidRegexp.JSON, message = "router JSON 数据格式错误！")
    private String routerDefault;

    @TableField("is_public")
    @Pattern(regexp = "[01]", message = "1 代表公开，0 代表私密！")
    private Integer isPublic;

    @TableField("is_legal")
    @Pattern(regexp = "[01]", message = "1 代表公开且审核通过，0 代表私密且审核未通过！")
    private Integer isLegal;

    @TableField("is_share")
    @Pattern(regexp = "[01]", message = "1 代表分享，0 代表私密！")
    private Integer isShare;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    @TableField("dead_share_date")
    private Timestamp deadShareDate;

    @TableField("user_id")
    private String userId;
}
