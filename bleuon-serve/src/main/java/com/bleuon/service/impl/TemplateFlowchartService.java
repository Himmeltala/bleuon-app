package com.bleuon.service.impl;

import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.TemplateFlowchartMapper;
import com.bleuon.service.ITemplateFlowchartService;
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
 * @date: 2023/10/5
 */
@RequiredArgsConstructor
@Service
public class TemplateFlowchartService implements ITemplateFlowchartService {

    private final TemplateFlowchartMapper mapper;

    @Override
    public R<List<TemplateFlowchart>> findAll(TemplateFlowchart data) {
        List<TemplateFlowchart> list = mapper.findAll(data);
        if (Objects.isNull(list)) return R.failed("没有查询到流程图！", null);
        return R.success(list);
    }

    @Override
    public R<TemplateFlowchart> findOne(TemplateFlowchart data) {
        TemplateFlowchart flowchart = mapper.findOne(data);
        if (Objects.isNull(flowchart)) return R.failed("没有查询到流程图！", null);
        return R.success(flowchart);
    }

    @Override
    @Transactional
    public boolean updateOne(TemplateFlowchart data) {
        try {
            Integer status = mapper.updateOne(data);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
