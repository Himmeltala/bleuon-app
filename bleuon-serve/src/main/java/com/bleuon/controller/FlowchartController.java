package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.BlueprintFlowchart;
import com.bleuon.entity.CollectingFlowchart;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.service.impl.CollectingFlowchartService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/9/29
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/flowchart")
public class FlowchartController {

    private final FlowchartService flowchartService;
    private final CollectingFlowchartService collectingFlowchartService;

    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade")
    public R<Object> upgrade(@RequestBody @Validated Flowchart body) {
        boolean status = flowchartService.upgrade(body);
        return status ? R.success("更新流程图成功！") : R.failed("更新流程图失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/by/id")
    public R<Flowchart> findById(@Validated Flowchart params) {
        Flowchart result = flowchartService.findById(params.getId());
        return Objects.isNull(result) ? R.failed("该流程图不存在！", null) : R.success(result);
    }

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @PostMapping("/find/all/by/criteria")
    public R<List<Flowchart>> findAllByCriteria(@RequestHeader(KeyVals.Token) String token,
                                                @RequestBody FlowchartCriteria criteria) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        criteria.setCollectingCid(consumerId);
        List<Flowchart> list = flowchartService.findAllByCriteria(criteria);
        return list.isEmpty() ? R.failed("没有查询到流程图！", null) : R.success(list);
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add")
    public R<Flowchart> add(@RequestHeader(KeyVals.Token) String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        Flowchart flowchart = flowchartService.add(consumerId);
        return flowchart != null ? R.success("创建流程图成功！", flowchart) : R.failed("创建流程图失败！", null);
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/replicate")
    public R<Flowchart> replicate(@RequestHeader(KeyVals.Token) String token,
                                  @RequestBody @Validated Flowchart body) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        Flowchart flowchart = flowchartService.replicate(body, consumerId);
        return flowchart != null ? R.success("复制流程图成功！", flowchart) : R.failed("复制流程图失败！", null);
    }

    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/by/id")
    public R<Object> deleteById(@Validated Flowchart params) {
        boolean status = flowchartService.deleteById(params.getId());
        return status ? R.success("删除流程图成功！") : R.failed("删除流程图失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @GetMapping("/find/all/collect/by/criteria")
    public R<List<Flowchart>> findAllCollectByCriteria(@RequestHeader(KeyVals.Token) String token,
                                                       @Validated FlowchartCriteria criteria) {
        Claims claims = JwtUtil.parseJwt(token);
        criteria.setCollectingCid((String) claims.get("id"));
        List<Flowchart> list = collectingFlowchartService.findAllByCriteria(criteria);
        return list != null ? R.success(list) : R.failed("没有收藏流程图！", null);
    }

    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/collect")
    public R<Object> deleteCollect(@RequestHeader(KeyVals.Token) String token,
                                   @Validated CollectingFlowchart params) {
        Claims claims = JwtUtil.parseJwt(token);
        params.setCollectingCid((String) claims.get("id"));
        boolean status = collectingFlowchartService.delete(params);
        return status ? R.success("删除收藏的流程图成功！") : R.failed("删除收藏的流程图失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/collect")
    public R<Object> addCollect(@RequestHeader(KeyVals.Token) String token,
                                @RequestBody @Validated CollectingFlowchart body) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        body.setCollectingCid(consumerId);
        return collectingFlowchartService.add(body);
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/release")
    public R<Object> release(@RequestBody @Validated BlueprintFlowchart body) {
        return flowchartService.release(body);
    }

    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
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
