package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CollectingConsumerMapper extends BaseMapper<CollectingConsumerModel> {

    List<CollectingConsumerModel> findAllByCriteria(ConsumerCriteria criteria);

    Integer add(CollectingConsumerModel model);

    Integer drop(CollectingConsumerModel model);

    CollectingConsumerModel findByCriteria(ConsumerCriteria criteria);

}
