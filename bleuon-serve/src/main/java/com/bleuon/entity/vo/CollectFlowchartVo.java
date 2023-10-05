package com.bleuon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class CollectFlowchartVo {

    private Integer id;
    private String collectUid;
    private String flowchartId;
    private Timestamp createDate;

}
