package com.bleuon.service;

import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/5
 */
public interface IBlueprintFlowchartService {

    R<List<BlueprintFlowchartModel>> findAll(BlueprintFlowchartModel model);

    R<BlueprintFlowchartModel> findById(BlueprintFlowchartModel model);

    boolean upgrade(BlueprintFlowchartModel model);

    boolean add(BlueprintFlowchartModel model);

    boolean delete(BlueprintFlowchartModel model);

}