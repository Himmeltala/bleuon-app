package com.bleuon.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
@CacheNamespace
public interface AuthorityMapper {

    List<String> getAuthority(Map<String, Object> map);

    boolean setAuthority(@Param("id") String id, @Param("role") Long role, @Param("uname") String uname);

}
