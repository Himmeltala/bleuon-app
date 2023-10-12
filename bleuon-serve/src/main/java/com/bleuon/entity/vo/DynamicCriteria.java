package com.bleuon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicCriteria {

    private String consumerId;

    private List<Sequence> sequences;

}
