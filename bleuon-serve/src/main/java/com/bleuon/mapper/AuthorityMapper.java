package com.bleuon.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@CacheNamespace
public interface AuthorityMapper {

    List<String> getAuthority(Map<String, Object> map);

    boolean setAuthority(String id, Long role, String uname);

}
