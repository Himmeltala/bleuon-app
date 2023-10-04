package com.bleuon.service;

import com.bleuon.entity.dto.CollectFlowchart;
import com.bleuon.entity.vo.FlowchartCondition;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectFlowchartService {

    List<CollectFlowchart> findAll(FlowchartCondition condition);

    boolean deleteOne(String id);

}
