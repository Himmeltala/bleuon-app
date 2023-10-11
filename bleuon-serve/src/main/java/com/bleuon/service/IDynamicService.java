package com.bleuon.service;

import com.bleuon.entity.Dynamic;
import com.bleuon.entity.vo.DynamicCriteria;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/10
 */
public interface IDynamicService {

    List<Dynamic> findAllByCriteria(DynamicCriteria criteria);

    boolean upgrade(Dynamic body);

    boolean deleteById(Dynamic params);

    boolean add(Dynamic body);
}
