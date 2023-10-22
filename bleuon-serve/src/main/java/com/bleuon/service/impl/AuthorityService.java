package com.bleuon.service.impl;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.service.IAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/22
 */
@Service
@RequiredArgsConstructor
public class AuthorityService implements IAuthorityService {

    private final AuthorityMapper authorityMapper;

    public List<AuthorityModel> findAdminAuthoritiesByAdminId(String adminId) {
        return authorityMapper.findAdminAuthoritiesByAdminId(adminId);
    }

    public List<AuthorityModel> findConsumerAuthoritiesByConsumerId(String consumerId) {
        return authorityMapper.findConsumerAuthoritiesByConsumerId(consumerId);
    }

    @Override
    public AdminModel findAdminAuthoritiesAll(String adminId) {
        return authorityMapper.findAdminAuthoritiesAll(adminId);
    }

    @Override
    public ConsumerModel findConsumerAuthoritiesAll(String consumerId) {
        return authorityMapper.findConsumerAuthoritiesAll(consumerId);
    }

}
