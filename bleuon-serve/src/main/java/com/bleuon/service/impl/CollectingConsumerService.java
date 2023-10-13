package com.bleuon.service.impl;

import com.bleuon.entity.CollectingConsumer;
import com.bleuon.entity.vo.ConsumerCriteria;
import com.bleuon.mapper.CollectingConsumerMapper;
import com.bleuon.service.ICollectingConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/13
 */
@Service
@RequiredArgsConstructor
public class CollectingConsumerService implements ICollectingConsumerService {

    private final CollectingConsumerMapper mapper;

    public List<CollectingConsumer> findAllByCriteria(ConsumerCriteria criteria) {
        return mapper.findAllByCriteria(criteria);
    }

}
