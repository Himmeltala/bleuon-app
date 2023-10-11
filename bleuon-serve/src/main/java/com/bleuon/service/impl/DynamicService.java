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
import java.util.UUID;

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
    public List<Dynamic> findAllByUid(String uid) {
        return query().eq("user_id", uid).list();
    }

    @Transactional
    @Override
    public boolean upgrade(Dynamic body) {
        try {
            Integer status = mapper.upgrade(body);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public boolean deleteById(Dynamic params) {
        try {
            return removeById(params.getId());
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public boolean add(Dynamic body) {
        try {
            String uuid = UUID.randomUUID().toString();
            body.setId(uuid);

            return save(body);
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
