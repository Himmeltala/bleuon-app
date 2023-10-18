package com.bleuon.controller.open;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "公开的流程图")
public class OpenFlowchartController {

    private final FlowchartService flowchartService;

    @Operation(summary = "查找分享的流程图")
    @GetMapping("/find/share")
    public R<FlowchartModel> findIsShare(@RequestParam String id) {
        return flowchartService.findIsShare(id);
    }

}
