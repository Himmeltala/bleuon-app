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
    public AdminModel findAdminWithRoleAndAuthorityList(PermissionCriteria criteria) {
        return permissionMapper.findAdminWithRoleAndAuthorityList(criteria);
    }

    @Override
    public PageInfo<AdminModel> findAdminsWithRoleAndAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAdminsWithRoleAndAuthorityList(criteria));
    }

    @Override
    public ConsumerModel findConsumerWithRoleAndAuthorityList(String consumerId) {
        return permissionMapper.findConsumerWithRoleAndAuthorityList(consumerId);
    }

    @Override
    public PageInfo<ConsumerModel> findAllConsumerWithRoleAndAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAllConsumerWithRoleAndAuthorityList(criteria));
    }

    @Override
    public PageInfo<RoleModel> findRolesWithAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findRolesWithAuthorityList(criteria));
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
    public PageInfo<AuthorityModel> findAuthoritiesOfRole(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        if (StringUtils.hasText(criteria.getAdminId())) {
            return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAdminAuthorityListByAdminId(criteria));
        } else {
            return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAuthorityListByRoleId(criteria.getRoleId()));
        }
    }

    @Override
    public PageInfo<RoleModel> findRolesWithoutAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);

        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findRolesWithoutAuthorityList(criteria));
    }

    @Override
    public List<AuthorityModel> findNoRepeatAuthorityListOfRole(PermissionCriteria criteria) {
        List<AuthorityModel> allAuthorityList = permissionMapper.findAuthorityList(criteria);
        List<AuthorityModel> roleAuthorityList = permissionMapper.findAuthorityListByRoleId(criteria.getRoleId());
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

    public boolean duplicateRole(PermissionCriteria criteria) {
        AdminModel admin = permissionMapper.findAdminWithRoleAndAuthorityList(criteria);
        List<RoleModel> roles = admin.getRoles();
        for (RoleModel role : roles) {
            if (role.getId().equals(criteria.getRoleId())) {
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
    public PageInfo<AdminModel> findAdminsWithRoleListWithoutAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAdminsWithRoleListWithoutAuthorityList(criteria));
    }

    @Override
    public PageInfo<AuthorityModel> findAuthorityList(PermissionCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> permissionMapper.findAuthorityList(criteria));
    }

    @Transactional
    @Override
    public boolean upgradeAuthority(AuthorityModel model) {
        try {
            Integer affectedRows = permissionMapper.upgradeAuthority(model);
            return affectedRows > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean dropAuthority(AuthorityModel model) {
        try {
            Integer affectedRows = permissionMapper.dropAuthority(model);
            return affectedRows > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Override
    public boolean addAuthority(AuthorityModel model) {
        try {
            Integer affectedRows = permissionMapper.addAuthority(model);
            return affectedRows > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }
}
