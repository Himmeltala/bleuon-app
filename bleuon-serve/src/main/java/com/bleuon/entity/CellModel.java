package com.bleuon.entity;

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

    @TableId
    private String id;

    private String notes;

    private Double width;

    private Double height;

    @Schema(description = "图形属性，refD 绘制 path 的重要参数")
    private String attrs;

    @Schema(description = "图形显示 viewBox，重要参数")
    private String viewBox;

    @Schema(description = "图形类型，basic、flowchart 等")
    @Pattern(regexp = ValidPattern.CELL_TYPE, message = "图形类型错误！")
    private String type;

    private Timestamp createDate;

    private Timestamp modifyDate;

}
