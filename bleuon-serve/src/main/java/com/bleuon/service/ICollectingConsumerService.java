package com.bleuon.service;

import com.bleuon.entity.CollectingConsumer;
import com.bleuon.entity.vo.ConsumerCriteria;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/13
 */
public interface ICollectingConsumerService {

    CollectingConsumer findByCriteria(ConsumerCriteria criteria);

    List<CollectingConsumer> findAllByCriteria(ConsumerCriteria criteria);

    boolean add(CollectingConsumer body);

    boolean delete(CollectingConsumer params);

}
