package com.bleuon.service;

import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/2
 */
public interface ICollectFlowchartService {

    List<CollectFlowchartDto> findAllCollectByCriteria(FlowchartCriteria criteria);

    boolean delete(CollectFlowchartVo data);

    R<Object> add(CollectFlowchartVo data);

}
