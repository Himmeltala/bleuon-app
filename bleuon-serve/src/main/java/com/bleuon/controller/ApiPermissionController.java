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
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AdminWithAuthorityList')")
    @GetMapping("/find/admin/with/authority/list")
    public R<AdminModel> findAdminWithAuthorityList(String adminId) {
        return R.success(permissionService.findAdminWithAuthorityList(adminId));
    }

    @Operation(summary = "获取所有管理员，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllAdminsWithAuthorityList')")
    @PostMapping("/find/all/admins/with/authority/list")
    public R<PageInfo<AdminModel>> findAllAdminsWithAuthorityList(@RequestBody PermissionCriteria criteria) {
        return R.success(permissionService.findAllAdminsWithAuthorityList(criteria));
    }

    @Operation(summary = "获取单个用户，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:ConsumerWithAuthorityList')")
    @GetMapping("/find/consumer/with/authority/list")
    public R<ConsumerModel> findConsumerWithAuthorityList(String consumerId) {
        return R.success(permissionService.findConsumerWithAuthorityList(consumerId));
    }

    @Operation(summary = "获取所有用户，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllConsumersWithAuthorityList')")
    @PostMapping("/find/all/consumers/with/authority/list")
    public R<PageInfo<ConsumerModel>> findAllConsumersWithAuthorityList(@RequestBody PermissionCriteria criteria) {
        return R.success(permissionService.findAllConsumersWithAuthorityList(criteria));
    }

    @Operation(summary = "查询角色分组，包括权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllRoleWithAuthorityList')")
    @PostMapping("/find/all/role/with/authority/list")
    public R<PageInfo<RoleModel>> findAllRoleWithAuthorityList(@RequestBody PermissionCriteria criteria) {
        return R.success(permissionService.findAllRoleWithAuthorityList(criteria));
    }

    @Operation(summary = "查询角色分组，但是没有权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllRole')")
    @PostMapping("/find/all/role")
    public R<PageInfo<RoleModel>> findAllRole(@RequestBody PermissionCriteria criteria) {
        return R.success(permissionService.findAllRole(criteria));
    }

    @Operation(summary = "查询角色的权限列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:RoleAuthorityList')")
    @PostMapping("/find/role/authority/list")
    public R<PageInfo<AuthorityModel>> findRoleAuthorityList(@RequestBody PermissionCriteria criteria) {
        return R.success(permissionService.findRoleAuthorityList(criteria));
    }

    @Operation(summary = "新增一个角色")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:permisson:role')")
    @PostMapping("/add/role")
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
    @DeleteMapping("/delete/role")
    public R<Object> deleteRole(RoleModel model) {
        boolean success = permissionService.deleteRole(model);
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "修改一个角色")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:permisson:role')")
    @PutMapping("/upgrade/role")
    public R<Object> upgradeRole(@RequestBody RoleModel model) {
        boolean success = permissionService.upgradeRole(model);
        return success ? R.success("修改成功！") : R.error("修改失败！");
    }

    @Operation(summary = "查询所有权限")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:permission:AllAuthorityList')")
    @GetMapping("/find/all/authority/list")
    public R<List<AuthorityModel>> findAllAuthorityList(PermissionCriteria criteria) {
        List<AuthorityModel> list = permissionService.findAllAuthorityList(criteria);
        return R.success(list);
    }

    @Operation(summary = "添加权限列表到权限角色关联表中")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:permission:AuthorityListToRole')")
    @PostMapping("/add/authority/list/to/role")
    public R<Object> addAuthorityListToRole(@RequestBody PermissionCriteria criteria) {
        boolean success = permissionService.addAuthorityListToRole(criteria);
        return success ? R.success("添加成功！") : R.error("添加失败！");
    }

}
