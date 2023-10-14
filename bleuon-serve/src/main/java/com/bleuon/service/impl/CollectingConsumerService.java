package com.bleuon.service.impl;

import com.bleuon.entity.CollectingConsumerModel;
import com.bleuon.entity.criterias.ConsumerCriteria;
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
    public CollectingConsumerModel findByCriteria(ConsumerCriteria criteria) {
        return mapper.findByCriteria(criteria);
    }

    @Override
    public List<CollectingConsumerModel> findAllByCriteria(ConsumerCriteria criteria) {
        return mapper.findAllByCriteria(criteria);
    }


    @Transactional
    @Override
    public boolean add(CollectingConsumerModel model) {
        try {
            String uuid = UUID.randomUUID().toString();
            model.setId(uuid);
            Integer added = mapper.add(model);
            return added > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    public boolean delete(CollectingConsumerModel model) {
        try {
            Integer deled = mapper.delete(model);
            return deled > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
