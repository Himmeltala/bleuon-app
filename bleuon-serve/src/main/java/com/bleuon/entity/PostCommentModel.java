package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bleuon.entity.dto.ConsumerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/14
 */
@Schema(description = "社区帖子评论模型")
@TableName("t_post_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentModel {

    @Schema(description = "UUID")
    @NotEmpty
    @TableId
    private String id;

    @Schema(description = "评论内容")
    @NotEmpty
    private String content;

    @Schema(description = "赞成数")
    private Integer digg;

    @Schema(description = "反对数")
    private Integer bury;

    @Schema(description = "创建日期")
    @TableField("create_date")
    private Timestamp createDate;

    @Schema(description = "修改日期")
    @TableField("modify_date")
    private Timestamp modifyDate;

    @Schema(description = "评论所属用户 ID")
    @NotEmpty
    @TableField("consumer_id")
    private String consumerId;

    @Schema(description = "评论所属用户模型")
    @TableField(exist = false)
    private ConsumerDTO consumer;

}
