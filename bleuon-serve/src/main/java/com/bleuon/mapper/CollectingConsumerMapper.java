package com.bleuon.mapper;

import com.bleuon.entity.CollectingConsumer;
import com.bleuon.entity.vo.ConsumerCriteria;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/13
 */
@Mapper
@CacheNamespace
public interface CollectingConsumerMapper {

    List<CollectingConsumer> findAllByCriteria(ConsumerCriteria criteria);

    Integer add(CollectingConsumer body);

    Integer delete(CollectingConsumer params);

    CollectingConsumer findByCriteria(ConsumerCriteria criteria);

}
