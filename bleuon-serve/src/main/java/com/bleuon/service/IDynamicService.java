package com.bleuon.service;

import com.bleuon.entity.Dynamic;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/10
 */
public interface IDynamicService {

    List<Dynamic> findAllByUid(String uid);

    boolean upgrade(Dynamic body);

    boolean deleteById(Dynamic params);

    boolean add(Dynamic body);
}
