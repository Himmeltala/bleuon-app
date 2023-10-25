package com.bleuon.entity.criterias;

import com.bleuon.entity.AdminModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity.criterias
 * @author: zheng
 * @date: 2023/10/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCriteria extends AdminModel implements Serializable {

    private Integer pageSize;
    private Integer currPage;
    private List<Sequence> sequences;

}
