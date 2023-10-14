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
 * @date: 2023/10/13
 */
@Schema(description = "用户条件查询模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerCriteria implements Serializable {

    @Schema(description = "用户 UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Schema(description = "收藏者 UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序条件")
    private List<Sequence> sequences;

}
