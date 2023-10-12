package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.Consumer;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 有关用户的 CRUD 功能
 *
 * @author zheng
 */
@Mapper
@CacheNamespace
public interface ConsumerMapper extends BaseMapper<Consumer> {

    Integer upgrade(Consumer vo);

}
