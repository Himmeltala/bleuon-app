package com.bleuon.service.impl;

import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.mapper.TemplateFlowchartMapper;
import com.bleuon.service.ITemplateFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<TemplateFlowchart> all = mapper.findAll(data);
        return R.success(all);
    }

}
