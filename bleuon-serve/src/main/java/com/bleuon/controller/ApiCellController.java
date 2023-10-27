package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.CellModel;
import com.bleuon.service.ICellService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.List;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/9/27
 */
@Tag(name = "流程图图形")
@RequiredArgsConstructor
@RequestMappingPrefix("/cell")
public class ApiCellController implements Serializable {

    private final ICellService cellService;

    @Operation(summary = "根据条件查询所有的流程图图形")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:cell:all')")
    @GetMapping("/find/all/by/criteria")
    public R<List<CellModel>> findAllByCriteria(@Validated CellModel model) {
        return R.success(cellService.findAllByCriteria(model));
    }

}
