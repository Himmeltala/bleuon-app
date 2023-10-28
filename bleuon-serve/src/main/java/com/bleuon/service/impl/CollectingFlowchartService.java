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

    private final CollectingFlowchartMapper collectingFlowchartMapper;

    @Override
    public List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria) {
        return collectingFlowchartMapper.findAllByCriteria(criteria);
    }

    @Override
    public FlowchartModel find(CollectingFlowchartModel model) {
        return collectingFlowchartMapper.find(model);
    }

    @Override
    public boolean drop(CollectingFlowchartModel model) {
        Integer row = collectingFlowchartMapper.drop(model);
        return row > 0;
    }

    @Transactional
    @Override
    public boolean add(CollectingFlowchartModel model) {
        try {
            Integer row = collectingFlowchartMapper.add(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
