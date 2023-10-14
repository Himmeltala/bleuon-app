package com.bleuon.service;

import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.criterias.FlowchartCriteria;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/29
 */
public interface IFlowchartService {

    boolean upgrade(FlowchartModel model);

    FlowchartModel findById(String flowchartId);

    R<FlowchartModel> findIsShare(String id);

    List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria);

    FlowchartModel add(String consumerId);

    FlowchartModel replicate(FlowchartModel data);

    boolean deleteById(String flowchartId);

    R<Object> release(BlueprintFlowchartModel model);

    R<Object> cancelRelease(String flowchartId);

}
