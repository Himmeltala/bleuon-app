package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CollectingFlowchartMapper extends BaseMapper<CollectingFlowchartModel> {

    List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria);

    Integer drop(CollectingFlowchartModel model);

    Integer add(CollectingFlowchartModel model);

    FlowchartModel find(CollectingFlowchartModel model);

}
