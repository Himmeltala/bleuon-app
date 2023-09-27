package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.OfficialCell;
import com.bleuon.service.IOfficialCellService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/9/27
 */
@Validated
@RequiredArgsConstructor
@RequestMappingPrefix("/cell/official")
public class CellController {

    private final IOfficialCellService service;

    @GetMapping("/query/all")
    public R<List<OfficialCell>> queryAllCells(@RequestParam String type) {
        return service.queryAllCells(type);
    }

}
