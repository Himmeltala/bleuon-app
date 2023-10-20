package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.entity.dto.ConsumerDTO;
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

    @Schema(description = "UUID")
    @TableId
    private String id;

    @Schema(description = "标题文本")
    private String title;

    @Schema(description = "标题 tag 集，JSON 字符串")
    @TableField("title_tag")
    private String titleTag;

    @Schema(description = "赞成数")
    private Integer digg;

    @Schema(description = "反对数")
    private Integer bury;

    @Schema(description = "浏览数")
    private Integer views;

    @Schema(description = "描述文本")
    private String desc;

    @Schema(description = "描述 tag 集，JSON 字符串")
    @TableField("desc_tag")
    private String descTag;

    @Schema(description = "描述图片，即封面图片，JSON 字符串")
    @TableField("desc_imgs")
    private String descImgs;

    @Schema(description = "帖子类型，如官方帖子、活动帖子")
    private String type;

    @Schema(description = "热门、推荐等类型")
    @TableField("ranking_type")
    private String rankingType;

    @Schema(description = "帖子内容")
    private String content;

    @Schema(description = "发表帖子者 ID")
    @TableField("consumer_id")
    private String consumerId;

    @Schema(description = "创建日期")
    @TableField("create_date")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "帖子所属用户模型")
    @TableField(exist = false)
    private ConsumerDTO consumer;

}
