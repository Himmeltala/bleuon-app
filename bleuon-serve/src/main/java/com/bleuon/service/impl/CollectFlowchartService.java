package com.bleuon.service.impl;

import com.bleuon.entity.dto.CollectFlowchart;
import com.bleuon.mapper.CollectFlowchartMapper;
import com.bleuon.service.ICollectFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/2
 */
@Service
@RequiredArgsConstructor
public class CollectFlowchartService implements ICollectFlowchartService {

    private final CollectFlowchartMapper mapper;

    @Override
    public R<List<CollectFlowchart>> findAll(String uid) {
        return R.success(mapper.queryAll(uid));
    }

}
