package com.bleuon.service;

import com.bleuon.entity.Flowchart;
import com.bleuon.entity.TemplateFlowchart;
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

    boolean updateOne(Flowchart data);

    Flowchart findOne(String id);

    R<Flowchart> exposeFindOne(String id);

    List<Flowchart> findAll(FlowchartCondition vo);

    Flowchart createOne(String userId);

    Flowchart cloneOne(Flowchart data, String uid);

    boolean deleteOne(String id);

    R releaseOne(TemplateFlowchart data);

    R cancelReleaseOne(String flowchartId);
}
