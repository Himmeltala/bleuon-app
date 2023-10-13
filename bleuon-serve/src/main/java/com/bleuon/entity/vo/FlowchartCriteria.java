package com.bleuon.entity.vo;

import com.bleuon.constant.ValidPattern;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowchartCriteria implements Serializable {

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;
    private String fileName;
    private Integer isPublic;
    private Integer isLegal;
    private Integer isShare;
    private List<Sequence> sequences;

}
