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
import org.springframework.util.StringUtils;

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
    public AdminModel findAdminWithRoleAndAuthorityList(String adminId) {
        return permissionMapper.findAdminWithRoleAndAuthorityList(adminId);
    }

    @Override
    public ConsumerModel findConsumerWithRoleAndAuthorityList(String consumerId) {
        return permissionMapper.findConsumerWithRoleAndAuthorityList(consumerId);
    }

    @Override
    public PageInfo<AdminModel> findAllAdminWithRoleAndAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllAdminWithRoleAndAuthorityList(criteria));
    }

    @Override
    public PageInfo<ConsumerModel> findAllConsumerWithRoleAndAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllConsumerWithRoleAndAuthorityList(criteria));
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
    public PageInfo<AuthorityModel> findAuthorityListOfRole(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        if (StringUtils.hasText(criteria.getAdminId())) {
            return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAdminAuthorityListByAdminId(criteria));
        } else {
            return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAdminAuthorityListByRoleId(criteria.getRoleId()));
        }
    }

    @Override
    public PageInfo<RoleModel> findAllRoleButNoAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllRoleButNoAuthorityList(criteria));
    }

    @Override
    public List<AuthorityModel> findAllAuthorityList(PermissionCriteria criteria) {
        List<AuthorityModel> allAuthorityList = permissionMapper.findAllAuthorityList();
        List<AuthorityModel> roleAuthorityList = permissionMapper.findAdminAuthorityListByRoleId(criteria.getRoleId());
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

    public boolean duplicateRole(String adminId, Integer roleId) {
        AdminModel admin = permissionMapper.findAdminWithRoleAndAuthorityList(adminId);
        List<RoleModel> roles = admin.getRoles();
        for (RoleModel role : roles) {
            if (role.getId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public boolean addRoleToAdmin(PermissionCriteria criteria) {
        try {
            Integer row = permissionMapper.addRoleToAdmin(criteria);
            return row > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteRoleOfAdmin(PermissionCriteria criteria) {
        try {
            Integer affectedRows = permissionMapper.deleteRoleOfAdmin(criteria);
            return affectedRows > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Override
    public PageInfo<AdminModel> findAllAdminWithRoleListButNoAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllAdminWithRoleListButNoAuthorityList(criteria));
    }

}
