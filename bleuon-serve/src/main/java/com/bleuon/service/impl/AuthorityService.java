package com.bleuon.service.impl;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.criterias.AuthorityCriteria;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.service.IAuthorityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<AuthorityModel> findAdminAuthorities(String adminId) {
        return authorityMapper.findAdminAuthorities(adminId);
    }

    public List<AuthorityModel> findConsumerAuthorities(String consumerId) {
        return authorityMapper.findConsumerAuthorities(consumerId);
    }

    @Override
    public AdminModel findAdmin(String adminId) {
        return authorityMapper.findAdmin(adminId);
    }

    @Override
    public ConsumerModel findConsumer(String consumerId) {
        return authorityMapper.findConsumer(consumerId);
    }

    @Override
    public PageInfo<AdminModel> findAllAdmin(AuthorityCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> authorityMapper.findAllAdmin(criteria));
    }

    @Override
    public PageInfo<ConsumerModel> findAllConsumer(AuthorityCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> authorityMapper.findAllConsumer(criteria));
    }

}
