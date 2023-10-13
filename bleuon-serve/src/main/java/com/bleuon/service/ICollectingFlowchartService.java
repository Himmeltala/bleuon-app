package com.bleuon.service;

import com.bleuon.entity.CollectingFlowchart;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectingFlowchartService {

    List<Flowchart> findAllByCriteria(FlowchartCriteria criteria);

    Flowchart find(CollectingFlowchart body);

    boolean delete(CollectingFlowchart data);

    boolean add(CollectingFlowchart data);

}
