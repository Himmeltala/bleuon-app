package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthUsernamePasswordMapper extends BaseMapper<User> {

    List<String> queryAuthorities(@Param("id") Long id, @Param("username") String username);

}
