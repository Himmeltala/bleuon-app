package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.BlueprintFlowchartModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/5
 */
@Mapper
public interface BlueprintFlowchartMapper extends BaseMapper<BlueprintFlowchartModel> {

    List<BlueprintFlowchartModel> findAll(BlueprintFlowchartModel model);

    BlueprintFlowchartModel find(BlueprintFlowchartModel model);

    Integer upgrade(BlueprintFlowchartModel model);

    Integer add(BlueprintFlowchartModel model);

    Integer drop(BlueprintFlowchartModel model);

}
