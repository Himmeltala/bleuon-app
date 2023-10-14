package com.bleuon.service.impl;

import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.BlueprintFlowchartMapper;
import com.bleuon.service.IBlueprintFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/5
 */
@RequiredArgsConstructor
@Service
public class BlueprintFlowchartService implements IBlueprintFlowchartService {

    private final BlueprintFlowchartMapper mapper;

    @Override
    public R<List<BlueprintFlowchartModel>> findAll(BlueprintFlowchartModel model) {
        List<BlueprintFlowchartModel> list = mapper.findAll(model);
        if (Objects.isNull(list)) return R.failed("没有查询到流程图！");
        return R.success(list);
    }

    @Override
    public R<BlueprintFlowchartModel> findById(BlueprintFlowchartModel model) {
        BlueprintFlowchartModel flowchart = mapper.find(model);
        if (Objects.isNull(flowchart)) return R.failed("没有查询到流程图！");
        return R.success(flowchart);
    }

    @Override
    @Transactional
    public boolean upgrade(BlueprintFlowchartModel model) {
        try {
            model.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = mapper.upgrade(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public boolean add(BlueprintFlowchartModel model) {
        try {
            Integer status = mapper.add(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public boolean delete(BlueprintFlowchartModel model) {
        try {
            Integer status = mapper.delete(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
