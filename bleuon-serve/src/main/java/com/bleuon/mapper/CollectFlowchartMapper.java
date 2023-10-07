package com.bleuon.mapper;

import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
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
public interface CollectFlowchartMapper {

    List<CollectFlowchartDto> findAllCollectByCriteria(FlowchartCriteria criteria);

    Integer erase(CollectFlowchartVo body);

    Integer add(CollectFlowchartVo body);

    CollectFlowchartDto find(CollectFlowchartVo body);

}
