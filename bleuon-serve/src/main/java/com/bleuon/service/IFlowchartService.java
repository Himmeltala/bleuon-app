package com.bleuon.service;

import com.bleuon.entity.Flowchart;
import com.bleuon.utils.http.R;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/29
 */
public interface IFlowchartService {

    R<Void> updateOne(Flowchart data);

    R<Flowchart> queryOne(String id);
}
