package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String id;

    @TableField("file_name")
    private String fileName;

    @TableField
    private String json;

    @TableField
    private String datauri;

    @TableField
    private Double width;

    @TableField
    private Double height;

    @TableField("is_public")
    private Integer isPublic;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    @TableField("user_id")
    private String userId;
}
