package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.BlueprintFlowchart;
import com.bleuon.entity.vo.CollectingFlowchartVo;
import com.bleuon.service.impl.CollectingFlowchartService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.service.impl.BlueprintFlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/find/all")
    public R<List<BlueprintFlowchart>> findAll(@Validated BlueprintFlowchart params) {
        String fileName = params.getFileName();
        if (fileName != null) {
            params.setFileName(fileName.toLowerCase());
        }
        return blueprintFlowchartService.findAll(params);
    }

    @GetMapping("/find/by/id")
    public R<BlueprintFlowchart> findById(@Validated BlueprintFlowchart params) {
        return blueprintFlowchartService.findById(params);
    }

    @PostMapping("/replicate")
    public R<Object> replicate(@RequestHeader(KeyVals.Token) String token,
                               @RequestBody @Validated BlueprintFlowchart body) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        body.setCopies(body.getCopies() + 1);

        Flowchart flowchart = flowchartService.replicate(body.getFlowchart(), consumerId);
        return Objects.isNull(flowchart) ? R.error("导入模板失败！") : R.success("导入模板成功！");
    }

    @PutMapping("/upgrade")
    public R<Object> upgrade(@RequestBody @Validated BlueprintFlowchart data) {
        boolean status = blueprintFlowchartService.upgrade(data);
        return status ? R.success("更新成功！") : R.failed("更新失败！");
    }

    @PostMapping("/add/collecting")
    public R<Object> addCollecting(@RequestHeader(KeyVals.Token) String token,
                                   @RequestBody @Validated BlueprintFlowchart data) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");

        R<Object> status = collectFlowchartService.add(new CollectingFlowchartVo(consumerId, data.getFlowchartId()));

        if (status.getCode() != 200) {
            return status;
        }

        data.setStars(data.getStars() + 1);
        boolean success = blueprintFlowchartService.upgrade(data);

        return success ? R.success("收藏成功！") : R.failed("收藏失败！");
    }

}
