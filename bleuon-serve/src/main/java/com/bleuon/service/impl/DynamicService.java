package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Dynamic;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.DynamicMapper;
import com.bleuon.service.IDynamicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/10
 */
@Service
@RequiredArgsConstructor
public class DynamicService extends ServiceImpl<DynamicMapper, Dynamic> implements IDynamicService {

    private final DynamicMapper mapper;

    @Override
    public List<Dynamic> findAll(String uid) {
        return query().eq("user_id", uid).list();
    }

    @Transactional
    @Override
    public boolean renewal(Dynamic data) {
        try {
            Integer status = mapper.renewal(data);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public boolean eraseDynamic(Dynamic params) {
        try {
            return removeById(params.getId());
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
