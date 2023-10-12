package com.bleuon.service;

import com.bleuon.entity.BlueprintFlowchart;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/5
 */
public interface IBlueprintFlowchartService {

    R<List<BlueprintFlowchart>> findAll(BlueprintFlowchart body);

    R<BlueprintFlowchart> findById(BlueprintFlowchart body);

    boolean upgrade(BlueprintFlowchart body);

    boolean add(BlueprintFlowchart body);

    boolean delete(BlueprintFlowchart body);

}