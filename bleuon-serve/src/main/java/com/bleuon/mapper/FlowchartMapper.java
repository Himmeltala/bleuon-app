package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.Flowchart;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/9/29
 */
@Mapper
@CacheNamespace
public interface FlowchartMapper extends BaseMapper<Flowchart> {

    /**
     * 更新流程图
     */
    Integer renewal(Flowchart data);

}
