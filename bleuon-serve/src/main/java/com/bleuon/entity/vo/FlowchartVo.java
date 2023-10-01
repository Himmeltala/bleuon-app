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

    private String uid;
    private String fileName;
    private Integer isPublic;
    private Integer isLegal;
    private Integer isShare;
    private List<Collate> collates;

}
