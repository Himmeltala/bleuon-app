package com.bleuon.controller.expose;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Flowchart;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @package: com.bleuon.controller.expose
 * @author: zheng
 * @date: 2023/9/30
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/public/flowchart")
public class OpenFlowchartController {

    private final FlowchartService service;

    @GetMapping("/find/share")
    public R<Flowchart> findIsShare(@RequestParam String id) {
        return service.findIsShare(id);
    }

}
