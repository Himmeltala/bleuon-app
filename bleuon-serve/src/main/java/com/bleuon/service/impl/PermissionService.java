package com.bleuon.service.impl;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.RoleModel;
import com.bleuon.entity.criterias.PermissionCriteria;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.PermissionMapper;
import com.bleuon.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public boolean addConsumerAuthority(String consumerId, Long roleId, String username) {
        try {
            Integer row = permissionMapper.addConsumerAuthority(consumerId, roleId, username);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
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

    @Transactional
    @Override
    public boolean addRole(RoleModel model) {
        try {
            Integer row = permissionMapper.addRole(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Override
    public RoleModel findRoleAnyFiled(RoleModel model) {
        return permissionMapper.findRoleAnyFiled(model);
    }

    @Transactional
    @Override
    public boolean deleteRole(RoleModel model) {
        try {
            Integer row = permissionMapper.deleteRole(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean upgradeRole(RoleModel model) {
        try {
            Integer row = permissionMapper.upgradeRole(model);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Override
    public PageInfo<AuthorityModel> findRoleAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findRoleAuthorityList(criteria.getRoleId()));
    }

    @Override
    public PageInfo<RoleModel> findAllRole(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllRole(criteria));
    }

    @Override
    public List<AuthorityModel> findAllAuthorityList(PermissionCriteria criteria) {
        List<AuthorityModel> allAuthorityList = permissionMapper.findAllAuthorityList();
        List<AuthorityModel> roleAuthorityList = permissionMapper.findRoleAuthorityList(criteria.getRoleId());
        allAuthorityList.removeAll(roleAuthorityList);
        return allAuthorityList;
    }

    @Transactional
    @Override
    public boolean addAuthorityListToRole(PermissionCriteria criteria) {
        try {
            Integer row = permissionMapper.addAuthorityListToRole(criteria);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteRoleAuthority(PermissionCriteria criteria) {
        try {
            Integer row = permissionMapper.deleteRoleAuthority(criteria);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
