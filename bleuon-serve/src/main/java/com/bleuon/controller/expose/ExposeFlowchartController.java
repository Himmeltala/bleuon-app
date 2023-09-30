package com.bleuon.controller.expose;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Flowchart;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 暴露 FlowchartController，不需要 Token 可以调用的接口
 * @package: com.bleuon.controller.expose
 * @author: zheng
 * @date: 2023/9/30
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/expose/flowchart")
public class ExposeFlowchartController {

    private final FlowchartService service;

    @GetMapping("/query/one")
    public R<Flowchart> exposeQueryOne(@RequestParam String id) {
        return service.exposeQueryOne(id);
    }

}
