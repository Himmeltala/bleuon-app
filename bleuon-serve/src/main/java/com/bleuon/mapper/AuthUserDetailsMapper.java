package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthUserDetailsMapper extends BaseMapper<User> {
}
