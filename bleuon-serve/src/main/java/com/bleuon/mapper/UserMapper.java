package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zheng
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
