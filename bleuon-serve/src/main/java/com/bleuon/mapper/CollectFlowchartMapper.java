package com.bleuon.mapper;

import com.bleuon.entity.dto.CollectFlowchart;
import com.bleuon.entity.vo.FlowchartCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/2
 */
@Mapper
public interface CollectFlowchartMapper {

    List<CollectFlowchart> findAll(FlowchartCondition condition);

    boolean deleteOne(String id);

}
