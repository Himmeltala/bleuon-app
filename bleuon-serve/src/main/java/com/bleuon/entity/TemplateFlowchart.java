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
 * @date: 2023/10/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_template_flowcharts")
public class TemplateFlowchart {

    @TableId
    private String id;
    private Integer views;
    private Integer copies;
    private Integer digg;
    private Integer stars;
    private String tags;
    private String scene;
    private String price;
    private String ranking;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("flowchart_id")
    private String flowchartId;

    @TableField(exist = false)
    private Flowchart flowchart;

    @TableField(exist = false)
    private String fileName;

}
