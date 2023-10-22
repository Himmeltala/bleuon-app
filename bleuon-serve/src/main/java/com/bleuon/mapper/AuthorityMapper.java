package com.bleuon.mapper;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityMapper {

    List<String> getConsumerAuthority(String consumerId, String username);

    List<String> getAdminAuthority(String adminId, String username);

    boolean setConsumerAuthority(String consumerId, Long roleId, String username);

    List<AuthorityModel> findAdminAuthoritiesByAdminId(String adminId);

    List<AuthorityModel> findConsumerAuthoritiesByConsumerId(String consumerId);

    AdminModel findAdminAuthoritiesAll(String adminId);

    ConsumerModel findConsumerAuthoritiesAll(String consumerId);

}
