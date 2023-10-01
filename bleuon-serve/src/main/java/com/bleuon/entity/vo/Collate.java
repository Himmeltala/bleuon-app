package com.bleuon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description: 升序或降序
 * @package: com.bleuon.entity.dto
 * @author: zheng
 * @date: 2023/9/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Collate {

    /**
     * 是否升序
     */
    private Boolean isAsc;

    /**
     * 字段名称
     */
    private String col;

}
