package com.bleuon.service.impl;

import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.entity.vo.FlowchartCondition;
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
    public List<CollectFlowchartDto> findAll(FlowchartCondition condition) {
        return mapper.findAll(condition);
    }

    @Override
    public boolean deleteOne(CollectFlowchartVo data) {
        Integer row = mapper.deleteOne(data);
        return row > 0;
    }

    @Override
    @Transactional
    public R<Void> addOne(CollectFlowchartVo data) {
        try {
            CollectFlowchartDto one = mapper.findOne(data);
            if (!Objects.isNull(one)) return R.failed("您已经收藏过了！");

            Integer row = mapper.addOne(data);
            if (row > 0) return R.success("收藏成功！");
            return R.error("收藏失败！");
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    public R<CollectFlowchartDto> findOne(CollectFlowchartVo data) {
        return null;
    }

}
