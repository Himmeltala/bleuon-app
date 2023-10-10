package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_dynamics")
public class Dynamic implements Serializable {

    @TableId
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer digg;
    private Integer bury;

    @TableField("create_date")
    private Timestamp createDate;

    @TableField("modify_date")
    private Timestamp modifyDate;

    @TableField("user_id")
    private String userId;

}
