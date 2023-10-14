package com.bleuon.mapper;

import com.bleuon.entity.CollectingConsumerModel;
import com.bleuon.entity.criterias.ConsumerCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/13
 */
@Mapper
public interface CollectingConsumerMapper {

    List<CollectingConsumerModel> findAllByCriteria(ConsumerCriteria criteria);

    Integer add(CollectingConsumerModel model);

    Integer delete(CollectingConsumerModel model);

    CollectingConsumerModel findByCriteria(ConsumerCriteria criteria);

}
