package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.AdminModel;
import com.bleuon.service.impl.AdminService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/23
 */
@Tag(name = "用户")
@RequiredArgsConstructor
@RequestMappingPrefix("/admin")
public class ApiAdminController {

    private final AdminService adminService;

    @Operation(summary = "查询管理员")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find')")
    @GetMapping("/find/{id}")
    public R<AdminModel> findById(@Validated AdminModel model) {
        AdminModel exists = adminService.findById(model);
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该管理员！");
    }

}
