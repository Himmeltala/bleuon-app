package com.bleuon.mapper;

import com.bleuon.entity.CollectingFlowchart;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/2
 */
@Mapper
@CacheNamespace
public interface CollectingFlowchartMapper {

    List<Flowchart> findAllByCriteria(FlowchartCriteria criteria);

    Integer delete(CollectingFlowchart body);

    Integer add(CollectingFlowchart body);

    Flowchart find(CollectingFlowchart body);

}
