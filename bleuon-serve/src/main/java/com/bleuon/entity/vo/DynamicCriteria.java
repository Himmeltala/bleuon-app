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
 * @date: 2023/10/12
 */
@Schema(description = "动态条件查询模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicCriteria implements Serializable {

    @Schema(description = "用户 UUID")
    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Schema(description = "排序条件")
    private List<Sequence> sequences;

}
