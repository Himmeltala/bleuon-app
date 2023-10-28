package com.bleuon.service;

import com.bleuon.entity.CollectingConsumerModel;
import com.bleuon.entity.criterias.ConsumerCriteria;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/13
 */
public interface ICollectingConsumerService {

    CollectingConsumerModel findByCriteria(ConsumerCriteria criteria);

    List<CollectingConsumerModel> findAllByCriteria(ConsumerCriteria criteria);

    boolean add(CollectingConsumerModel model);

    boolean drop(CollectingConsumerModel model);

}
