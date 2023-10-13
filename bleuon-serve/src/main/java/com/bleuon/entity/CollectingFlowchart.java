package com.bleuon.entity;

import com.bleuon.constant.ValidPattern;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectingFlowchart implements Serializable {

    private Integer id;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String flowchartId;

    private Timestamp createDate;

    public CollectingFlowchart(String collectingCid, String flowchartId) {
        this.collectingCid = collectingCid;
        this.flowchartId = flowchartId;
    }

}
