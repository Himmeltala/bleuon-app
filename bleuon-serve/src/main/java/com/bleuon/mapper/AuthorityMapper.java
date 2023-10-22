package com.bleuon.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityMapper {

    List<String> getConsumerAuthority(String consumerId, String username);

    List<String> getAdminAuthority(String adminId, String username);

    boolean setConsumerAuthority(String consumerId, Long roleId, String username);

}
