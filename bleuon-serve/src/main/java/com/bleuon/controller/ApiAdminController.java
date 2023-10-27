package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.criterias.AdminCriteria;
import com.bleuon.service.IAdminService;
import com.bleuon.utils.http.R;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/23
 */
@Tag(name = "管理员")
@RequiredArgsConstructor
@RequestMappingPrefix("/admin")
public class ApiAdminController {

    private final IAdminService adminService;

    @Operation(summary = "查询管理员，通过多个字段查询")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:admin:ByFiled')")
    @GetMapping("/find/by")
    public R<AdminModel> findBy(AdminModel model) {
        return R.success(adminService.findBy(model));
    }

    @Operation(summary = "查询管理员列表，通过多个字段查询")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:admin:ListByFiled')")
    @PostMapping("/find/list/by")
    public R<PageInfo<AdminModel>> findListBy(@RequestBody AdminCriteria criteria) {
        return R.success(adminService.findListBy(criteria));
    }

}
