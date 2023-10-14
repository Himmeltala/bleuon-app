package com.bleuon.service.impl;

import com.bleuon.entity.CollectingFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.criterias.FlowchartCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.CollectingFlowchartMapper;
import com.bleuon.service.ICollectingFlowchartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/2
 */
@Service
@RequiredArgsConstructor
public class CollectingFlowchartService implements ICollectingFlowchartService {

    private final CollectingFlowchartMapper mapper;

    @Override
    public List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria) {
        return mapper.findAllByCriteria(criteria);
    }

    @Override
    public FlowchartModel find(CollectingFlowchartModel model) {
        return mapper.find(model);
    }

    @Override
    public boolean delete(CollectingFlowchartModel model) {
        Integer row = mapper.delete(model);
        return row > 0;
    }

    @Override
    @Transactional
    public boolean add(CollectingFlowchartModel model) {
        try {
            Integer row = mapper.add(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
