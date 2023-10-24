package com.bleuon.entity.criterias;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/14
 */
@Schema(description = "条件查询基本模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonCriteria implements Serializable {

    private Integer pageSize;
    private Integer currPage;
    private List<Sequence> sequences;

}
