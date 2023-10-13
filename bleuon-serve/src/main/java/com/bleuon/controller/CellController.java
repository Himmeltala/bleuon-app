package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Cell;
import com.bleuon.service.impl.CellService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/9/27
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/cell")
@Tag(name = "流程图图形")
public class CellController {

    private final CellService service;

    @Operation(summary = "根据条件查询所有的流程图图形")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all/by/criteria")
    public R<List<Cell>> findAllByCriteria(@Validated Cell params) {
        return service.findAllByCriteria(params);
    }

}
