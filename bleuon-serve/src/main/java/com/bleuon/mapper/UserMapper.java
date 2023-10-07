package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 有关用户的 CRUD 功能
 *
 * @author zheng
 */
@Mapper
@CacheNamespace
public interface UserMapper extends BaseMapper<User> {

    Integer updateOne(User vo);

}
