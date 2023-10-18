package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.entity.CollectingFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.service.impl.BlueprintFlowchartService;
import com.bleuon.service.impl.CollectingFlowchartService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/5
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/blueprint")
@Tag(name = "流程图模板")
public class BlueprintController {

    private final FlowchartService flowchartService;
    private final CollectingFlowchartService collectFlowchartService;
    private final BlueprintFlowchartService blueprintFlowchartService;

    @Operation(summary = "查询所有流程图模板")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:blueprint-all')")
    @GetMapping("/find/all")
    public R<List<BlueprintFlowchartModel>> findAll(@Validated BlueprintFlowchartModel model) {
        String filename = model.getFileName();
        if (StringUtils.hasText(filename)) {
            model.setFileName(filename.toLowerCase());
        }
        return blueprintFlowchartService.findAll(model);
    }

    @Operation(summary = "根据流程图模板 ID 查询单个流程图模板")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:blueprint-detail')")
    @GetMapping("/find/by/id")
    public R<BlueprintFlowchartModel> findById(@Validated BlueprintFlowchartModel model) {
        return blueprintFlowchartService.findById(model);
    }

    @Operation(summary = "导入流程图模板为个人流程图", description = "consumerId 必须是导入人的 UID")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:blueprint-import')")
    @PostMapping("/replicate/{consumerId}")
    public R<Object> replicate(@PathVariable String consumerId,
                               @RequestBody @Validated BlueprintFlowchartModel model) {
        model.setCopies(model.getCopies() + 1);

        model.getFlowchart().setConsumerId(consumerId);
        FlowchartModel success = flowchartService.replicate(model.getFlowchart());
        return Objects.isNull(success) ? R.error("导入模板失败！") : R.success("导入模板成功！");
    }

    @Operation(summary = "更新流程图模板，如点赞")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:blueprint')")
    @PutMapping("/upgrade")
    public R<Object> upgrade(@RequestBody @Validated BlueprintFlowchartModel model) {
        boolean status = blueprintFlowchartService.upgrade(model);
        return status ? R.success("更新成功！") : R.failed("更新失败！");
    }

    @Operation(summary = "收藏流程图模板")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:blueprint-collect')")
    @PostMapping("/add/collecting/{consumerId}")
    public R<Object> addCollecting(@PathVariable String consumerId,
                                   @RequestBody @Validated BlueprintFlowchartModel model) {
        CollectingFlowchartModel collectingFlowchart = new CollectingFlowchartModel(consumerId, model.getFlowchartId());
        FlowchartModel exists = collectFlowchartService.find(collectingFlowchart);
        if (!Objects.isNull(exists)) return R.failed("您已经收藏过了！");

        boolean added = collectFlowchartService.add(collectingFlowchart);
        if (!added) R.error("收藏失败！");

        model.setStars(model.getStars() + 1);
        boolean upgraded = blueprintFlowchartService.upgrade(model);
        return upgraded ? R.success("收藏成功！") : R.failed("收藏失败！");
    }

}
