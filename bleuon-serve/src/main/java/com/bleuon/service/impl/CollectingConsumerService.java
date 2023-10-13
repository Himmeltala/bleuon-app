package com.bleuon.service.impl;

import com.bleuon.entity.CollectingConsumer;
import com.bleuon.entity.vo.ConsumerCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.CollectingConsumerMapper;
import com.bleuon.service.ICollectingConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<CollectingConsumer> findAllByCriteria(ConsumerCriteria criteria) {
        return mapper.findAllByCriteria(criteria);
    }

    @Transactional
    @Override
    public boolean add(CollectingConsumer body) {
        try {
            String uuid = UUID.randomUUID().toString();
            body.setId(uuid);
            Integer added = mapper.add(body);
            return added > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
