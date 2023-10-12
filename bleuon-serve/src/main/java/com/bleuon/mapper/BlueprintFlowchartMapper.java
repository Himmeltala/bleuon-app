package com.bleuon.mapper;

import com.bleuon.entity.BlueprintFlowchart;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/5
 */
@Mapper
@CacheNamespace
public interface BlueprintFlowchartMapper {

    List<BlueprintFlowchart> findAll(BlueprintFlowchart params);

    BlueprintFlowchart find(BlueprintFlowchart params);

    Integer upgrade(BlueprintFlowchart data);

    Integer add(BlueprintFlowchart data);

    Integer delete(BlueprintFlowchart data);
}
