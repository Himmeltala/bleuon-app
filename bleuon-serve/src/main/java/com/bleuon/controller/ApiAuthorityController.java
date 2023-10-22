package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.criterias.AuthorityCriteria;
import com.bleuon.service.impl.AuthorityService;
import com.bleuon.utils.http.R;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/22
 */
@Tag(name = "权限")
@RequiredArgsConstructor
@RequestMappingPrefix("/authority")
public class ApiAuthorityController implements Serializable {

    private final AuthorityService authorityService;

    @Operation(summary = "仅获取管理员的权限")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:authority-admin-auth-list')")
    @GetMapping("/find/admin/authorities")
    public R<List<AuthorityModel>> findAdminAuthorities(String adminId) {
        return R.success(authorityService.findAdminAuthorities(adminId));
    }

    @Operation(summary = "仅获取用户的权限")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:authority-consumer-auth-list')")
    @GetMapping("/find/consumer/authorities")
    public R<List<AuthorityModel>> findConsumerAuthorities(String consumerId) {
        return R.success(authorityService.findConsumerAuthorities(consumerId));
    }

    @Operation(summary = "获取单个管理员，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:authority-admin')")
    @GetMapping("/find/admin")
    public R<AdminModel> findAdmin(String adminId) {
        return R.success(authorityService.findAdmin(adminId));
    }

    @Operation(summary = "获取所有管理员，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:authority-admin-all')")
    @PostMapping("/find/all/admin")
    public R<PageInfo<AdminModel>> findAllAdmin(@RequestBody AuthorityCriteria criteria) {
        return R.success(authorityService.findAllAdmin(criteria));
    }

    @Operation(summary = "获取单个用户，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:authority-consumer')")
    @GetMapping("/find/consumer")
    public R<ConsumerModel> findConsumer(String consumerId) {
        return R.success(authorityService.findConsumer(consumerId));
    }

    @Operation(summary = "获取所有用户，包括权限和角色")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:authority-consumer-all')")
    @PostMapping("/find/all/consumer")
    public R<PageInfo<ConsumerModel>> findAllConsumer(@RequestBody AuthorityCriteria criteria) {
        return R.success(authorityService.findAllConsumer(criteria));
    }

}
