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
 * @description: 官方图形实体类
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/9/27
 */
@Data
@TableName("t_official_cells")
@NoArgsConstructor
@AllArgsConstructor
public class OfficialCell implements Serializable {

    @TableId
    private Integer id;

    @TableField
    private String notes;

    @TableField
    private Double width;

    @TableField
    private Double height;

    @Pattern(regexp = ValidPattern.JSON, message = "不是合法的 JSON 字符串！")
    @TableField
    private String attrs;

    @TableField("view_box")
    private String viewBox;

    @Pattern(regexp = ValidPattern.CELL_TYPE, message = "图形类型错误！")
    @TableField
    private String type;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

}
