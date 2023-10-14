package com.bleuon.mapper;

import com.bleuon.entity.CollectingFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.criterias.FlowchartCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/2
 */
@Mapper
public interface CollectingFlowchartMapper {

    List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria);

    Integer delete(CollectingFlowchartModel model);

    Integer add(CollectingFlowchartModel model);

    FlowchartModel find(CollectingFlowchartModel model);

}
