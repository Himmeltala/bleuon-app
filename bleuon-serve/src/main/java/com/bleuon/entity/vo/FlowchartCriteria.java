package com.bleuon.entity.vo;

import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/1
 */
@Schema(description = "流程图条件查询模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowchartCriteria implements Serializable {

    @Schema(description = "收藏者 UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "是否公开")
    private Integer isPublic;

    @Schema(description = "是否审核通过")
    private Integer isLegal;

    @Schema(description = "是否分享")
    private Integer isShare;

    @Schema(description = "使用场景")
    private String scene;

    @Schema(description = "价格，比如免费、VIP免费")
    private String price;

    @Schema(description = "排序类型，比如热门、推荐")
    private String ranking;

    @Schema(description = "排序条件")
    private List<Sequence> sequences;

}
