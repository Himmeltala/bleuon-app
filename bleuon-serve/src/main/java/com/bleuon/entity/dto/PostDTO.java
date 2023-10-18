package com.bleuon.entity.dto;

import com.bleuon.entity.PostCommentModel;
import com.bleuon.entity.PostModel;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.entity.dto
 * @author: zheng
 * @date: 2023/10/19
 */
@Schema(description = "社区帖子模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {

    private PostModel post;
    private PageInfo<PostCommentModel> comments;

}