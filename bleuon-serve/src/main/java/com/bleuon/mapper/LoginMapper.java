package com.bleuon.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginMapper {

    List<String> queryAuthorities(Map<String, Object> map);

}
