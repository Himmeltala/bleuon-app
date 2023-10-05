package com.bleuon.mapper;

import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
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

    List<CollectFlowchartDto> findAll(FlowchartCondition condition);

    Integer deleteOne(String id);

    Integer addOne(CollectFlowchartVo data);

    CollectFlowchartDto findOne(CollectFlowchartVo data);

}
