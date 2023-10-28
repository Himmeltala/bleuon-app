package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.entity.CollectingFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.criterias.FlowchartCriteria;
import com.bleuon.service.ICollectingFlowchartService;
import com.bleuon.service.IFlowchartService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/9/29
 */
@Tag(name = "流程图")
@RequiredArgsConstructor
@RequestMappingPrefix("/flowchart")
public class ApiFlowchartController implements Serializable {

    private final IFlowchartService flowchartService;
    private final ICollectingFlowchartService collectingFlowchartService;

    @Operation(summary = "更新流程图")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:upgrade:flowchart')")
    @PutMapping("/upgrade")
    public R<Object> upgrade(@RequestBody @Validated FlowchartModel model) {
        boolean status = flowchartService.upgrade(model);
        return status ? R.success("更新流程图成功！") : R.failed("更新流程图失败！");
    }

    @Operation(summary = "根据 ID 查询流程图")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:flowchart')")
    @GetMapping("/find/by/id")
    public R<FlowchartModel> findById(@Validated FlowchartModel model) {
        FlowchartModel result = flowchartService.findById(model.getId());
        return Objects.isNull(result) ? R.failed("该流程图不存在！") : R.success(result);
    }

    @Operation(summary = "根据条件查询所有流程图")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:flowchart')")
    @PostMapping("/find/all/by/criteria")
    public R<List<FlowchartModel>> findAllByCriteria(@Validated @RequestBody FlowchartCriteria criteria) {
        List<FlowchartModel> list = flowchartService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "新增一个流程图", description = "consumerId 必填")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:flowchart')")
    @PostMapping("/add")
    public R<FlowchartModel> add(@RequestBody FlowchartModel model) {
        FlowchartModel flowchart = flowchartService.add(model.getConsumerId());
        return Objects.isNull(flowchart) ? R.error("创建流程图失败！") : R.success("创建流程图成功！", flowchart);
    }

    @Operation(summary = "将流程图导入到个人文件中")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:flowchart:replicate')")
    @PostMapping("/replicate")
    public R<FlowchartModel> replicate(@RequestBody @Validated FlowchartModel model) {
        FlowchartModel success = flowchartService.replicate(model);
        return Objects.isNull(success) ? R.error("复制流程图失败！") : R.success("复制流程图成功！", success);
    }

    @Operation(summary = "删除单个流程图")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:flowchart')")
    @DeleteMapping("/delete/by/id")
    public R<Object> deleteById(@Validated FlowchartModel model) {
        boolean status = flowchartService.deleteById(model.getId());
        return status ? R.success("删除流程图成功！") : R.failed("删除流程图失败！");
    }

    @Operation(summary = "根据条件查询所有收藏的流程图")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:find:flowchart:collect')")
    @GetMapping("/find/all/collect/by/criteria")
    public R<List<FlowchartModel>> findAllCollectByCriteria(@Validated FlowchartCriteria criteria) {
        List<FlowchartModel> list = collectingFlowchartService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "删除收藏的流程图", description = "flowchartId、collectorId 必填")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:flowchart:collect')")
    @DeleteMapping("/delete/collecting")
    public R<Object> deleteCollecting(@Validated CollectingFlowchartModel model) {
        boolean status = collectingFlowchartService.drop(model);
        return status ? R.success("删除成功！") : R.failed("删除失败！");
    }

    @Operation(summary = "收藏一个流程图", description = "flowchartId、collectorId 必填")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:flowchart:collect')")
    @PostMapping("/add/collecting")
    public R<Object> addCollecting(@RequestBody @Validated CollectingFlowchartModel model) {
        FlowchartModel exists = collectingFlowchartService.find(model);
        if (!Objects.isNull(exists)) return R.failed("您已经收藏过了！");

        boolean added = collectingFlowchartService.add(model);
        return added ? R.success("收藏成功！") : R.failed("收藏失败！");
    }

    @Operation(summary = "把流程图公开到模板社区")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:add:flowchart:relase')")
    @PostMapping("/release")
    public R<Object> release(@RequestBody @Validated BlueprintFlowchartModel model) {
        return flowchartService.release(model);
    }

    @Operation(summary = "取消已公开到模板社区的流程图")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:delete:flowchart:release')")
    @DeleteMapping("/cancel/release")
    public R<Object> cancelRelease(
            @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
            @Validated
            @RequestParam
            String flowchartId
    ) {
        return flowchartService.cancelRelease(flowchartId);
    }

}
