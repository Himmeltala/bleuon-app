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

    AdminModel findAdminWithAuthorityList(String adminId);

    ConsumerModel findConsumerWithAuthorityList(String consumerId);

    PageInfo<AdminModel> findAllAdminsWithAuthorityList(PermissionCriteria criteria);

    PageInfo<ConsumerModel> findAllConsumersWithAuthorityList(PermissionCriteria criteria);

    PageInfo<RoleModel> findAllRoleWithAuthorityList(PermissionCriteria criteria);

    boolean addRole(RoleModel model);

    RoleModel findRoleAnyFiled(RoleModel model);

    boolean deleteRole(RoleModel model);

    boolean upgradeRole(RoleModel model);

    PageInfo<AuthorityModel> findRoleAuthorityList(PermissionCriteria criteria);

    PageInfo<RoleModel> findAllRole(PermissionCriteria criteria);

}
