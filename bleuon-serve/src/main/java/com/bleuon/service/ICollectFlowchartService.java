package com.bleuon.service;

import com.bleuon.entity.dto.CollectFlowchart;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectFlowchartService {

    R<List<CollectFlowchart>> findAll(String uid);

}
