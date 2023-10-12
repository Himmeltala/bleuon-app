package com.bleuon.service.impl;

import com.bleuon.entity.BlueprintFlowchart;
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
    public R<List<BlueprintFlowchart>> findAll(BlueprintFlowchart params) {
        List<BlueprintFlowchart> list = mapper.findAll(params);
        if (Objects.isNull(list)) return R.failed("没有查询到流程图！", null);
        return R.success(list);
    }

    @Override
    public R<BlueprintFlowchart> findById(BlueprintFlowchart params) {
        BlueprintFlowchart flowchart = mapper.find(params);
        if (Objects.isNull(flowchart)) return R.failed("没有查询到流程图！", null);
        return R.success(flowchart);
    }

    @Override
    @Transactional
    public boolean upgrade(BlueprintFlowchart body) {
        try {
            body.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = mapper.upgrade(body);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public boolean add(BlueprintFlowchart body) {
        try {
            Integer status = mapper.add(body);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public boolean delete(BlueprintFlowchart body) {
        try {
            Integer status = mapper.delete(body);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
