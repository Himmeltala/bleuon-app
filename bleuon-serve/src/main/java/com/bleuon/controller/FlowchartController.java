package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Flowchart;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/9/29
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/flowchart")
public class FlowchartController {

    private final FlowchartService service;

    @PostMapping("/update/one")
    public R<Void> updateOne(@RequestBody Flowchart data) {
        return service.updateOne(data);
    }

    @GetMapping("/query/one")
    public R<Flowchart> queryOne(@RequestParam String id) {
        return service.queryOne(id);
    }

}
