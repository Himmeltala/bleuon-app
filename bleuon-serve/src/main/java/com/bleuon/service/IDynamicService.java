package com.bleuon.service;

import com.bleuon.entity.DynamicModel;
import com.bleuon.entity.criterias.DynamicCriteria;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/10
 */
public interface IDynamicService {

    List<DynamicModel> findAllByCriteria(DynamicCriteria criteria);

    boolean upgrade(DynamicModel model);

    boolean deleteById(DynamicModel model);

    boolean add(DynamicModel model);
}
