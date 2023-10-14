package com.bleuon.entity.criterias;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/12
 */
@Schema(description = "排序条件模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence implements Serializable {

    @Schema(description = "是否升序")
    private Boolean isAsc;

    @Schema(description = "升序字段名称")
    private String col;

}
