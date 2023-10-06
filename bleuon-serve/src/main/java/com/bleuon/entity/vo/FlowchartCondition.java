package com.bleuon.entity.vo;

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
public class FlowchartCondition implements Serializable {

    private String uid;
    private String fileName;
    private Integer isPublic;
    private Integer isLegal;
    private Integer isShare;
    private List<Collate> collates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Collate implements Serializable {

        /**
         * 是否升序
         */
        private Boolean isAsc;

        /**
         * 字段名称
         */
        private String col;

    }

}
