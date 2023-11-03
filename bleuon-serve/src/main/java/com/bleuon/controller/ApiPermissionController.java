package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.RoleModel;
import com.bleuon.entity.criterias.PermissionCriteria;
import com.bleuon.service.impl.PermissionService;
import com.bleuon.utils.http.R;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/24
 */
@Tag(name = "权限和角色")
@RequiredArgsConstructor
@RequestMappingPrefix("/permission")
public class ApiPermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "获取单个管理员，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AdminWithRoleAndAuthorityList')")
    @GetMapping("/admin/with/role-and-authority-list")
    public R<AdminModel> findAdminWithRoleAndAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findAdminWithRoleAndAuthorityList(criteria));
    }

    @Operation(summary = "获取所有管理员，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllAdminWithRoleAndAuthorityList')")
    @GetMapping("/admins/with/role-and-authority-list")
    public R<PageInfo<AdminModel>> findAdminsWithRoleAndAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findAdminsWithRoleAndAuthorityList(criteria));
    }

    @Operation(summary = "获取所有管理员，仅包括角色不包括权限")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllAdminWithRoleListButNoAuthorityList')")
    @GetMapping("/admins/with/role-list/without/authority-list")
    public R<PageInfo<AdminModel>> findAdminsWithRoleListWithoutAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findAdminsWithRoleListWithoutAuthorityList(criteria));
    }

    @Operation(summary = "获取单个用户，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:ConsumerWithRoleAndAuthorityList')")
    @GetMapping("/consumer/with/role-and-authority-list")
    public R<ConsumerModel> findConsumerWithRoleAndAuthorityList(String consumerId) {
        return R.success(permissionService.findConsumerWithRoleAndAuthorityList(consumerId));
    }

    @Operation(summary = "获取所有用户，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllConsumerWithRoleAndAuthorityList')")
    @GetMapping("/consumers/with/role-and-authority-list")
    public R<PageInfo<ConsumerModel>> findAllConsumerWithRoleAndAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findAllConsumerWithRoleAndAuthorityList(criteria));
    }

    @Operation(summary = "查询角色分组，包括权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllRoleWithAuthorityList')")
    @GetMapping("/roles/with-authority-list")
    public R<PageInfo<RoleModel>> findRolesWithAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findRolesWithAuthorityList(criteria));
    }

    @Operation(summary = "查询角色分组，但是没有权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllRoleButNoAuthorityList')")
    @GetMapping("/roles/without/authority-list")
    public R<PageInfo<RoleModel>> findRolesWithoutAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findRolesWithoutAuthorityList(criteria));
    }

    @Operation(summary = "查询角色的权限列表，可以通过 roleId 或者 adminId")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AuthorityListOfRole')")
    @GetMapping("/authorities/of/role")
    public R<PageInfo<AuthorityModel>> findAuthoritiesOfRole(PermissionCriteria criteria) {
        return R.success(permissionService.findAuthoritiesOfRole(criteria));
    }

    @Operation(summary = "新增一个角色")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:permisson:role')")
    @PostMapping("/role")
    public R<Object> addRole(@RequestBody RoleModel model) {
        RoleModel exists = permissionService.findRoleAnyFiled(model);

        if (!Objects.isNull(exists)) {
            return R.error("不能重复添加角色！");
        }

        boolean success = permissionService.addRole(model);
        return success ? R.success("新增成功！") : R.error("新增失败！");
    }

    @Operation(summary = "删除一个角色")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:permisson:role')")
    @DeleteMapping("/role")
    public R<Object> deleteRole(RoleModel model) {
        boolean success = permissionService.deleteRole(model);
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "修改一个角色")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:permisson:role')")
    @PutMapping("/role")
    public R<Object> upgradeRole(@RequestBody RoleModel model) {
        boolean success = permissionService.upgradeRole(model);
        return success ? R.success("修改成功！") : R.error("修改失败！");
    }

    @Operation(summary = "查询角色没有的权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AuthorityListOfRoleButNohave')")
    @GetMapping("/no-repeat/authorities/of/role")
    public R<List<AuthorityModel>> findNoRepeatAuthorityListOfRole(PermissionCriteria criteria) {
        List<AuthorityModel> list = permissionService.findNoRepeatAuthorityListOfRole(criteria);
        return R.success(list);
    }

    @Operation(summary = "添加权限列表到权限角色关联表中")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:permission:AuthorityListToRole')")
    @PostMapping("/authority/list/to/role")
    public R<Object> addAuthorityListToRole(@RequestBody PermissionCriteria criteria) {
        boolean success = permissionService.addAuthorityListToRole(criteria);
        return success ? R.success("添加成功！") : R.error("添加失败！");
    }

    @Operation(summary = "删除角色权限")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:permission:RoleAuthority')")
    @DeleteMapping("/role/authority")
    public R<Object> deleteRoleAuthority(PermissionCriteria criteria) {
        boolean success = permissionService.deleteRoleAuthority(criteria);
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "将角色分配给管理员")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:permission:RoleToAdmin')")
    @PostMapping("/role/to/admin")
    public R<Object> addRoleToAdmin(@RequestBody PermissionCriteria criteria) {
        boolean duplicated = permissionService.duplicateRole(criteria);

        if (duplicated) {
            return R.error("不能重复添加角色！");
        }

        boolean success = permissionService.addRoleToAdmin(criteria);
        return success ? R.success("添加成功！") : R.error("添加失败！");
    }

    @Operation(summary = "删除管理员的角色，可以删除整个管理员所拥有的角色")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:permission:RoleOfAdmin')")
    @DeleteMapping("/role-of-admin")
    public R<Object> deleteRoleOfAdmin(PermissionCriteria criteria) {
        boolean success = permissionService.deleteRoleOfAdmin(criteria);
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "查询权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AuthorityList')")
    @GetMapping("/authority-list")
    public R<PageInfo<AuthorityModel>> findAuthorityList(PermissionCriteria criteria) {
        return R.success(permissionService.findAuthorityList(criteria));
    }

    @Operation(summary = "更新权限")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:permission:Authority')")
    @PutMapping("/authority")
    public R<Object> upgradeAuthority(@RequestBody AuthorityModel model) {
        boolean success = permissionService.upgradeAuthority(model);
        return success ? R.success("更新成功！") : R.error("更新失败！");
    }

    @Operation(summary = "删除权限")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:permission:Authority')")
    @DeleteMapping("/authority")
    public R<Object> dropAuthority(AuthorityModel model) {
        boolean success = permissionService.dropAuthority(model);
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "添加权限")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:permission:Authority')")
    @PostMapping("/authority")
    public R<Object> addAuthority(@RequestBody AuthorityModel model) {
        boolean success = permissionService.addAuthority(model);
        return success ? R.success("添加成功！") : R.error("添加失败！");
    }

}
