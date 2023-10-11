package com.bleuon.entity.vo;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence implements Serializable {

    /**
     * 是否升序
     */
    private Boolean isAsc;

    /**
     * 字段名称
     */
    private String col;

}
