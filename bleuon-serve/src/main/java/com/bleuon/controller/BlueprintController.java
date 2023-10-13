package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.BlueprintFlowchart;
import com.bleuon.entity.CollectingFlowchart;
import com.bleuon.entity.Flowchart;
import com.bleuon.service.impl.BlueprintFlowchartService;
import com.bleuon.service.impl.CollectingFlowchartService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
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
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all")
    public R<List<BlueprintFlowchart>> findAll(@Validated BlueprintFlowchart params) {
        String filename = params.getFileName();
        if (StringUtils.hasText(filename)) {
            params.setFileName(filename.toLowerCase());
        }
        return blueprintFlowchartService.findAll(params);
    }

    @Operation(summary = "根据流程图模板 ID 查询单个流程图模板")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/by/id")
    public R<BlueprintFlowchart> findById(@Validated BlueprintFlowchart params) {
        return blueprintFlowchartService.findById(params);
    }

    @Operation(summary = "导入流程图模板为个人流程图")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/replicate")
    public R<Object> replicate(@RequestHeader(KeyVals.Token) String token,
                               @RequestBody @Validated BlueprintFlowchart body) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        body.setCopies(body.getCopies() + 1);

        Flowchart flowchart = flowchartService.replicate(body.getFlowchart(), consumerId);
        return Objects.isNull(flowchart) ? R.error("导入模板失败！") : R.success("导入模板成功！");
    }

    @Operation(summary = "更新流程图模板，如点赞")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade")
    public R<Object> upgrade(@RequestBody @Validated BlueprintFlowchart data) {
        boolean status = blueprintFlowchartService.upgrade(data);
        return status ? R.success("更新成功！") : R.failed("更新失败！");
    }

    @Operation(summary = "收藏流程图模板")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/collecting")
    public R<Object> addCollecting(@RequestHeader(KeyVals.Token) String token,
                                   @RequestBody @Validated BlueprintFlowchart body) {
        Claims claims = JwtUtil.parseJwt(token);
        CollectingFlowchart collectingFlowchart = new CollectingFlowchart((String) claims.get("id"), body.getFlowchartId());
        Flowchart exists = collectFlowchartService.find(collectingFlowchart);
        if (!Objects.isNull(exists)) return R.failed("您已经收藏过了！");

        boolean added = collectFlowchartService.add(collectingFlowchart);
        if (!added) R.error("收藏失败！");

        body.setStars(body.getStars() + 1);
        boolean upgraded = blueprintFlowchartService.upgrade(body);
        return upgraded ? R.success("收藏成功！") : R.failed("收藏失败！");
    }

}
