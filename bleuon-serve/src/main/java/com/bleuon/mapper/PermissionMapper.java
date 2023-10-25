package com.bleuon.mapper;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.RoleModel;
import com.bleuon.entity.criterias.PermissionCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/24
 */
@Mapper
public interface PermissionMapper {

    List<String> findConsumerAuthorityList(String consumerId, String username);

    List<String> findAdminAuthorityList(String adminId, String username);

    Integer addConsumerAuthority(String consumerId, Long roleId, String username);

    AdminModel findAdminWithRoleAndAuthorityList(String adminId);

    ConsumerModel findConsumerWithRoleAndAuthorityList(String consumerId);

    List<AdminModel> findAllAdminWithRoleAndAuthorityList(PermissionCriteria criteria);

    List<ConsumerModel> findAllConsumerWithRoleAndAuthorityList(PermissionCriteria criteria);

    List<RoleModel> findAllRoleWithAuthorityList(PermissionCriteria criteria);

    Integer addRole(RoleModel model);

    RoleModel findRoleAnyFiled(RoleModel model);

    Integer deleteRole(RoleModel model);

    Integer upgradeRole(RoleModel model);

    List<AuthorityModel> findAuthorityListOfRole(String roleId);

    List<RoleModel> findAllRoleButNoAuthorityList(PermissionCriteria criteria);

    List<AuthorityModel> findAllAuthorityList();

    Integer addAuthorityListToRole(PermissionCriteria criteria);

    Integer deleteRoleAuthority(PermissionCriteria criteria);

    Integer addRoleToAdmin(PermissionCriteria criteria);

}