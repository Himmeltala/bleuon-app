package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidPattern;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_flowcharts")
public class Flowchart implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    @TableId
    private String id;

    @TableField("file_name")
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

    @Pattern(regexp = ValidPattern.JSON, message = "网格配置 JSON 格式错误！")
    @TableField("draw_grid")
    private String drawGrid;

    @Pattern(regexp = ValidPattern.JSON, message = "JSON 数据格式错误！")
    @TableField("connector_default")
    private String connectorDefault;

    @Pattern(regexp = ValidPattern.JSON, message = "JSON 数据格式错误！")
    @TableField("router_default")
    private String routerDefault;

    @TableField("is_public")
    private Integer isPublic;

    @TableField("is_legal")
    private Integer isLegal;

    @TableField("is_share")
    private Integer isShare;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    @TableField("dead_share_date")
    private Timestamp deadShareDate;

    @TableField("user_id")
    private String userId;

    @TableField(exist = false)
    private User user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User implements Serializable {
        private String id;
        private String username;
        private String avatar;
    }

}
