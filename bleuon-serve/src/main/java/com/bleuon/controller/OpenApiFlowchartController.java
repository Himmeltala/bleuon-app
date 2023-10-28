package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.service.IFlowchartService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller.expose
 * @author: zheng
 * @date: 2023/9/30
 */
@Tag(name = "公开的流程图")
@RequiredArgsConstructor
@RequestMappingPrefix("/public/flowchart")
public class OpenApiFlowchartController implements Serializable {

    private final IFlowchartService flowchartService;

    @Operation(summary = "查找分享的流程图")
    @GetMapping("/find/share")
    public R<FlowchartModel> findIsShare(@RequestParam String id) {
        return flowchartService.findIsShare(id);
    }

}
