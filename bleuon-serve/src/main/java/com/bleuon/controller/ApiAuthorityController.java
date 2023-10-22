package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.AuthorityModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.service.impl.AuthorityService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

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

    @Operation(summary = "获取管理员的权限")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:admin-authorities')")
    @GetMapping("/find/admin")
    public R<List<AuthorityModel>> findAdminAuthoritiesByAdminId(String adminId) {
        return R.success(authorityService.findAdminAuthoritiesByAdminId(adminId));
    }

    @Operation(summary = "获取用户的权限")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:consumer-authorities')")
    @GetMapping("/find/consumer")
    public R<List<AuthorityModel>> findConsumerAuthoritiesByConsumerId(String consumerId) {
        return R.success(authorityService.findConsumerAuthoritiesByConsumerId(consumerId));
    }

    @Operation(summary = "获取管理员权限连同获取管理员的模型数据")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:admin-authorities')")
    @GetMapping("/find/admin/all")
    public R<AdminModel> findAdminAuthoritiesAll(String adminId) {
        return R.success(authorityService.findAdminAuthoritiesAll(adminId));
    }

    @Operation(summary = "获取用户权限连同获取用户的模型数据")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:consumer-authorities')")
    @GetMapping("/find/consumer/all")
    public R<ConsumerModel> findConsumerAuthoritiesAll(String consumerId) {
        return R.success(authorityService.findConsumerAuthoritiesAll(consumerId));
    }

}
