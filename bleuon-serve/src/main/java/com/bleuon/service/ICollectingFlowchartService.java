package com.bleuon.service;

import com.bleuon.entity.dto.CollectingFlowchartDto;
import com.bleuon.entity.vo.CollectingFlowchartVo;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectingFlowchartService {

    List<CollectingFlowchartDto> findAllByCriteria(FlowchartCriteria criteria);

    boolean delete(CollectingFlowchartVo data);

    R<Object> add(CollectingFlowchartVo data);

}
