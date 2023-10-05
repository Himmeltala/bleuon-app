package com.bleuon.mapper;

import com.bleuon.entity.TemplateFlowchart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/5
 */
@Mapper
public interface TemplateFlowchartMapper {

    List<TemplateFlowchart> findAll(TemplateFlowchart data);

}
