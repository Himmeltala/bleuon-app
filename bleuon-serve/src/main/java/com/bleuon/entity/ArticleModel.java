package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/14
 */
@Schema(description = "社区帖子模型")
@TableName("t_articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleModel implements Serializable {

    @TableId
    private String id;

    private String title;

    private String titleTag;

    private Integer digg;

    private Integer bury;

    private Integer views;

    private String desc;

    private String descTag;

    private String descImgs;

    @Schema(description = "帖子类型，如官方帖子、活动帖子")
    private String type;

    @Schema(description = "热门、推荐等类型")
    private String rankingType;

    private String content;

    private String consumerId;

    private Timestamp createDate;

    private Timestamp modifyDate;

    @TableField(exist = false)
    private ConsumerModel consumer;

}
