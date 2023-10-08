package com.bleuon.mapper;

import com.bleuon.entity.TemplateFlowchart;
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
public interface TemplateFlowchartMapper {

    List<TemplateFlowchart> findAll(TemplateFlowchart params);

    TemplateFlowchart find(TemplateFlowchart params);

    Integer renewal(TemplateFlowchart data);

    Integer add(TemplateFlowchart data);

    Integer erase(TemplateFlowchart data);
}
