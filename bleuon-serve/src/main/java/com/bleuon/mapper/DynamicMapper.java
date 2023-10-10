package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.Dynamic;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/10
 */
@Mapper
@CacheNamespace
public interface DynamicMapper extends BaseMapper<Dynamic> {
}
