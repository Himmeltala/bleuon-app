package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.FlowchartModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/9/29
 */
@Mapper
public interface FlowchartMapper extends BaseMapper<FlowchartModel> {

    Integer upgrade(FlowchartModel model);

}
