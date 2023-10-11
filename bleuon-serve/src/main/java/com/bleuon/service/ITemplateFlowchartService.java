package com.bleuon.service;

import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/5
 */
public interface ITemplateFlowchartService {

    R<List<TemplateFlowchart>> findAll(TemplateFlowchart body);

    R<TemplateFlowchart> findById(TemplateFlowchart body);

    boolean upgrade(TemplateFlowchart body);

    boolean add(TemplateFlowchart body);

    boolean delete(TemplateFlowchart body);

}