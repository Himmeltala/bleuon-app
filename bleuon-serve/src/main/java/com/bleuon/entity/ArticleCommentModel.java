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
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/14
 */
@Schema(description = "社区帖子评论模型")
@TableName("t_article_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentModel implements Serializable {

    @TableId
    private String id;

    private String content;

    private Integer digg;

    private Integer bury;

    private Timestamp createDate;

    private Timestamp modifyDate;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String articleId;

    @TableField(exist = false)
    private ConsumerModel consumer;

}
