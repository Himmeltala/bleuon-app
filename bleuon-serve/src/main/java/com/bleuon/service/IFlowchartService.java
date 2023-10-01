package com.bleuon.service;

import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartVo;
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

    R<Flowchart> queryOne(String id);

    R<Flowchart> exposeQueryOne(String id);

    R<List<Flowchart>> queryAll(FlowchartVo vo);

    R<Flowchart> createOne(String userId);

    R<Flowchart> copyOne(Flowchart data, String userId);

    R<Void> deleteOne(String id);
}
