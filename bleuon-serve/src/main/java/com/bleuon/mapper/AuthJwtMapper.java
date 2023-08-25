package com.bleuon.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuthJwtMapper {

    List<String> getAuthorities(Map<String, Object> map);

}
