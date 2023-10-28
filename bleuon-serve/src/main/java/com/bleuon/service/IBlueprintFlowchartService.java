package com.bleuon.service;

import com.bleuon.entity.BlueprintFlowchartModel;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/5
 */
public interface IBlueprintFlowchartService {

    List<BlueprintFlowchartModel> findAll(BlueprintFlowchartModel model);

    BlueprintFlowchartModel findById(BlueprintFlowchartModel model);

    boolean upgrade(BlueprintFlowchartModel model);

    boolean add(BlueprintFlowchartModel model);

    boolean drop(BlueprintFlowchartModel model);

}