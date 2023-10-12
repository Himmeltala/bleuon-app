package com.bleuon.service;

import com.bleuon.entity.BlueprintFlowchart;
import com.bleuon.entity.Flowchart;
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

    boolean upgrade(Flowchart body);

    Flowchart findById(String flowchartId);

    R<Flowchart> findIsShare(String id);

    List<Flowchart> findAllByCriteria(FlowchartCriteria criteria);

    Flowchart add(String consumerId);

    Flowchart replicate(Flowchart data, String consumerId);

    boolean deleteById(String flowchartId);

    R<Object> release(BlueprintFlowchart body);

    R<Object> cancelRelease(String flowchartId);

}
