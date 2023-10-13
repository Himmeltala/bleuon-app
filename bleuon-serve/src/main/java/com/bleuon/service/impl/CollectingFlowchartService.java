package com.bleuon.service.impl;

import com.bleuon.entity.CollectingFlowchart;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.CollectingFlowchartMapper;
import com.bleuon.service.ICollectingFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public List<Flowchart> findAllByCriteria(FlowchartCriteria criteria) {
        return mapper.findAllByCriteria(criteria);
    }

    @Override
    public Flowchart find(CollectingFlowchart body) {
        return mapper.find(body);
    }

    @Override
    public boolean delete(CollectingFlowchart body) {
        Integer row = mapper.delete(body);
        return row > 0;
    }

    @Override
    @Transactional
    public boolean add(CollectingFlowchart body) {
        try {
            Integer row = mapper.add(body);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
