package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.constant.ValidRegexp;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class OfficialCell {

    @TableId("id")
    private Integer id;

    @TableField("notes")
    private String notes;

    @TableField("width")
    private Double width;

    @TableField("height")
    private Double height;

    @TableField("attrs")
    @Pattern(regexp = ValidRegexp.JSON, message = "似乎不是一个合法的 JSON 字符串！")
    private String attrs;

    @TableField("view_box")
    private String viewBox;

    @TableField("type")
    @Pattern(regexp = "official|custom", message = "图形类型只能是 official、custom！")
    private String type;

}
