package com.bleuon.service.impl;

import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.CollectFlowchartMapper;
import com.bleuon.service.ICollectFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public List<CollectFlowchartDto> findAllCollectByCriteria(FlowchartCriteria criteria) {
        return mapper.findAllCollectByCriteria(criteria);
    }

    @Override
    public boolean erase(CollectFlowchartVo body) {
        Integer row = mapper.erase(body);
        return row > 0;
    }

    @Override
    @Transactional
    public R<Object> add(CollectFlowchartVo body) {
        try {
            CollectFlowchartDto one = mapper.find(body);
            if (!Objects.isNull(one)) return R.failed("您已经收藏过了！");

            Integer row = mapper.add(body);
            if (row > 0) return R.success("收藏成功！");
            return R.error("收藏失败！");
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
