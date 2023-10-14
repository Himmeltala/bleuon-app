package com.bleuon.entity.criterias;

import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/14
 */

@Schema(description = "评论条件查询模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DiscussionCriteria extends CommonCriteria implements Serializable {

    @Schema(description = "用户 UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Schema(description = "帖子 UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String postId;

    @Schema(description = "帖子标题")
    private String title;

}
