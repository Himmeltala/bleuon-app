package com.bleuon.mapper;

import com.bleuon.entity.dto.CollectingFlowchartDto;
import com.bleuon.entity.vo.CollectingFlowchartVo;
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

    List<CollectingFlowchartDto> findAllByCriteria(FlowchartCriteria criteria);

    Integer delete(CollectingFlowchartVo body);

    Integer add(CollectingFlowchartVo body);

    CollectingFlowchartDto find(CollectingFlowchartVo body);

}
