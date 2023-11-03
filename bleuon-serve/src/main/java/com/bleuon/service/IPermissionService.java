package com.bleuon.service;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.RoleModel;
import com.bleuon.entity.criterias.PermissionCriteria;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/24
 */
public interface IPermissionService {

    List<String> findConsumerAuthorityList(String consumerId, String username);

    List<String> findAdminAuthorityList(String adminId, String username);

    boolean addConsumerAuthority(String consumerId, Long roleId, String username);

    AdminModel findAdminWithRoleAndAuthorityList(PermissionCriteria criteria);

    ConsumerModel findConsumerWithRoleAndAuthorityList(String consumerId);

    PageInfo<AdminModel> findAdminsWithRoleAndAuthorityList(PermissionCriteria criteria);

    PageInfo<ConsumerModel> findAllConsumerWithRoleAndAuthorityList(PermissionCriteria criteria);

    PageInfo<RoleModel> findRolesWithAuthorityList(PermissionCriteria criteria);

    boolean addRole(RoleModel model);

    RoleModel findRoleAnyFiled(RoleModel model);

    boolean deleteRole(RoleModel model);

    boolean upgradeRole(RoleModel model);

    PageInfo<AuthorityModel> findAuthoritiesOfRole(PermissionCriteria criteria);

    PageInfo<RoleModel> findRolesWithoutAuthorityList(PermissionCriteria criteria);

    List<AuthorityModel> findNoRepeatAuthorityListOfRole(PermissionCriteria criteria);

    boolean addAuthorityListToRole(PermissionCriteria criteria);

    boolean deleteRoleAuthority(PermissionCriteria criteria);

    boolean addRoleToAdmin(PermissionCriteria criteria);

    boolean deleteRoleOfAdmin(PermissionCriteria criteria);

    PageInfo<AdminModel> findAdminsWithRoleListWithoutAuthorityList(PermissionCriteria criteria);

    PageInfo<AuthorityModel> findAuthorityList(PermissionCriteria criteria);

    boolean upgradeAuthority(AuthorityModel model);

    boolean dropAuthority(AuthorityModel model);

    boolean addAuthority(AuthorityModel model);

}
