package com.bleuon.mapper;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.criterias.AuthorityCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityMapper {

    List<String> getConsumerAuthority(String consumerId, String username);

    List<String> getAdminAuthority(String adminId, String username);

    boolean setConsumerAuthority(String consumerId, Long roleId, String username);

    List<AuthorityModel> findAdminAuthorities(String adminId);

    List<AuthorityModel> findConsumerAuthorities(String consumerId);

    AdminModel findAdmin(String adminId);

    ConsumerModel findConsumer(String consumerId);

    List<AdminModel> findAllAdmin(AuthorityCriteria criteria);

    List<ConsumerModel> findAllConsumer(AuthorityCriteria criteria);

}
