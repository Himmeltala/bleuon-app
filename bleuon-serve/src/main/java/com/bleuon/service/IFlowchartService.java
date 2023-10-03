package com.bleuon.service;

import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCondition;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/29
 */
public interface IFlowchartService {

    R<Void> updateOne(Flowchart data);

    R<Flowchart> findOne(String id);

    R<Flowchart> exposeFindOne(String id);

    R<List<Flowchart>> findAll(FlowchartCondition vo);

    R<Flowchart> createOne(String userId);

    R<Flowchart> cloneOne(Flowchart data, String userId);

    R<Void> deleteOne(String id);
}
