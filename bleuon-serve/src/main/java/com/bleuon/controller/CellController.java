package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Cell;
import com.bleuon.service.impl.CellService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
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
public class CellController {

    private final CellService service;

    @GetMapping("/find/all/by/criteria")
    public R<List<Cell>> findAllByCriteria(@Validated Cell params) {
        return service.findAllByCriteria(params);
    }

}
