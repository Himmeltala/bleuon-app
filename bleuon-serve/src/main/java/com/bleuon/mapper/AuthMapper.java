package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapper extends BaseMapper<User> {

    List<String> queryAuthsByUserId(@Param("userId") Long userId, @Param("username") String username);

}
