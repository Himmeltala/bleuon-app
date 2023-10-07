package com.bleuon.service;

import com.bleuon.entity.Flowchart;
import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/29
 */
public interface IFlowchartService {

    boolean renewal(Flowchart body);

    Flowchart findById(String flowchartId);

    R<Flowchart> exposeFindOne(String id);

    List<Flowchart> findAllByCriteria(FlowchartCriteria criteria);

    Flowchart add(String uid);

    Flowchart replicate(Flowchart data, String uid);

    boolean eraseById(String flowchartId);

    R<Object> release(TemplateFlowchart body);

    R<Object> cancelRelease(String flowchartId);
}
