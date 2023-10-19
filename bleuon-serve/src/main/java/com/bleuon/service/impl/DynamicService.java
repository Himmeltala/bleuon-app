package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.DynamicModel;
import com.bleuon.entity.criterias.DynamicCriteria;
import com.bleuon.entity.criterias.Sequence;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.DynamicMapper;
import com.bleuon.service.IDynamicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/10
 */
@Service
@RequiredArgsConstructor
public class DynamicService extends ServiceImpl<DynamicMapper, DynamicModel> implements IDynamicService {

    private final DynamicMapper dynamicMapper;

    @Override
    public List<DynamicModel> findAllByCriteria(DynamicCriteria criteria) {
        QueryWrapper<DynamicModel> wrapper = new QueryWrapper<>();
        wrapper.eq("consumer_id", criteria.getConsumerId());

        List<Sequence> sequences = criteria.getSequences();
        if (sequences != null) {
            sequences.forEach(e -> wrapper.orderBy(true, e.getIsAsc(), e.getCol()));
        }

        return super.list(wrapper);
    }

    @Transactional
    @Override
    public boolean upgrade(DynamicModel model) {
        try {
            model.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = dynamicMapper.upgrade(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteById(DynamicModel model) {
        try {
            return removeById(model.getId());
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean add(DynamicModel model) {
        try {
            String uuid = UUID.randomUUID().toString();
            model.setId(uuid);

            return save(model);
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
