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
public class BlueprintController {

    private final FlowchartService flowchartService;
    private final CollectingFlowchartService collectFlowchartService;
    private final BlueprintFlowchartService blueprintFlowchartService;

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all")
    public R<List<BlueprintFlowchart>> findAll(@Validated BlueprintFlowchart params) {
        String filename = params.getFileName();
        if (StringUtils.hasText(filename)) {
            params.setFileName(filename.toLowerCase());
        }
        return blueprintFlowchartService.findAll(params);
    }

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/by/id")
    public R<BlueprintFlowchart> findById(@Validated BlueprintFlowchart params) {
        return blueprintFlowchartService.findById(params);
    }

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

    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade")
    public R<Object> upgrade(@RequestBody @Validated BlueprintFlowchart data) {
        boolean status = blueprintFlowchartService.upgrade(data);
        return status ? R.success("更新成功！") : R.failed("更新失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/collecting")
    public R<Object> addCollecting(@RequestHeader(KeyVals.Token) String token,
                                   @RequestBody @Validated BlueprintFlowchart data) {
        Claims claims = JwtUtil.parseJwt(token);
        R<Object> status = collectFlowchartService.add(new CollectingFlowchart((String) claims.get("id"), data.getFlowchartId()));

        if (status.getCode() != 200) {
            return R.error("收藏失败！");
        }

        data.setStars(data.getStars() + 1);
        boolean success = blueprintFlowchartService.upgrade(data);

        return success ? R.success("收藏成功！") : R.failed("收藏失败！");
    }

}
