package com.bleuon.service.impl;

import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.BlueprintFlowchartMapper;
import com.bleuon.service.IBlueprintFlowchartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/5
 */
@RequiredArgsConstructor
@Service
public class BlueprintFlowchartService implements IBlueprintFlowchartService {

    private final BlueprintFlowchartMapper blueprintFlowchartMapper;

    @Override
    public List<BlueprintFlowchartModel> findAll(BlueprintFlowchartModel model) {
        return blueprintFlowchartMapper.findAll(model);
    }

    @Override
    public BlueprintFlowchartModel findById(BlueprintFlowchartModel model) {
        return blueprintFlowchartMapper.find(model);
    }

    @Override
    @Transactional
    public boolean upgrade(BlueprintFlowchartModel model) {
        try {
            model.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = blueprintFlowchartMapper.upgrade(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean add(BlueprintFlowchartModel model) {
        try {
            Integer status = blueprintFlowchartMapper.add(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean drop(BlueprintFlowchartModel model) {
        try {
            Integer status = blueprintFlowchartMapper.drop(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
