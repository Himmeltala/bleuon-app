package com.bleuon.service.impl;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.RoleModel;
import com.bleuon.entity.criterias.PermissionCriteria;
import com.bleuon.mapper.PermissionMapper;
import com.bleuon.service.IPermissionService;
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
 * @date: 2023/10/24
 */
@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public List<String> findConsumerAuthorityList(String consumerId, String username) {
        return permissionMapper.findConsumerAuthorityList(consumerId, username);
    }

    @Override
    public List<String> findAdminAuthorityList(String adminId, String username) {
        return permissionMapper.findAdminAuthorityList(adminId, username);
    }

    @Override
    public boolean addConsumerAuthority(String consumerId, Long roleId, String username) {
        return permissionMapper.addConsumerAuthority(consumerId, roleId, username);
    }

    @Override
    public AdminModel findAdminWithAuthorityList(String adminId) {
        return permissionMapper.findAdminWithAuthorityList(adminId);
    }

    @Override
    public ConsumerModel findConsumerWithAuthorityList(String consumerId) {
        return permissionMapper.findConsumerWithAuthorityList(consumerId);
    }

    @Override
    public PageInfo<AdminModel> findAllAdminsWithAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllAdminsWithAuthorityList(criteria));
    }

    @Override
    public PageInfo<ConsumerModel> findAllConsumersWithAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllConsumersWithAuthorityList(criteria));
    }

    @Override
    public PageInfo<RoleModel> findAllRoleWithAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllRoleWithAuthorityList(criteria));
    }

}
