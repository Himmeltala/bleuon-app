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
 * @description: 官方图形实体类
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/9/27
 */
@Schema(description = "图形模型")
@TableName("t_cells")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CellModel implements Serializable {

    @Schema(description = "图形 ID")
    @TableId
    private String id;

    @Schema(description = "备注图形")
    @TableField
    private String notes;

    @Schema(description = "图形宽度")
    @TableField
    private Double width;

    @Schema(description = "图形高度")
    @TableField
    private Double height;

    @Schema(description = "图形属性，refD 绘制 path 的重要参数")
    @TableField
    private String attrs;

    @Schema(description = "图形显示 viewBox，重要参数")
    @TableField("view_box")
    private String viewBox;

    @Schema(description = "图形类型，basic、flowchart 等")
    @Pattern(regexp = ValidPattern.CELL_TYPE, message = "图形类型错误！")
    @TableField
    private String type;

    @Schema(description = "创建日期")
    @TableField("create_date")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    @TableField("modify_date")
    private Timestamp modifyDate;

}
