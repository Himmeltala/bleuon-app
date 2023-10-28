package com.bleuon.service;

import com.bleuon.entity.CollectingFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.criterias.FlowchartCriteria;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectingFlowchartService {

    List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria);

    FlowchartModel find(CollectingFlowchartModel model);

    boolean drop(CollectingFlowchartModel model);

    boolean add(CollectingFlowchartModel model);

}
