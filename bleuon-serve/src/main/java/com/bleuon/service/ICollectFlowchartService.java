package com.bleuon.service;

import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.entity.vo.FlowchartCondition;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectFlowchartService {

    List<CollectFlowchartDto> findAll(FlowchartCondition condition);

    boolean deleteOne(CollectFlowchartVo data);

    R<Void> addOne(CollectFlowchartVo data);

    R<CollectFlowchartDto> findOne(CollectFlowchartVo data);
}
