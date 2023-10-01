package com.bleuon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class FlowchartVo {

    private String fileName;
    private List<Collate> collates;

}
