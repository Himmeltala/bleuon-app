package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.service.impl.CollectFlowchartService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.service.impl.TemplateFlowchartService;
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
@RequestMappingPrefix("/community/template")
public class TemplateCommunityController {

    private final FlowchartService flowchartService;
    private final CollectFlowchartService collectFlowchartService;
    private final TemplateFlowchartService templateFlowchartService;

    @GetMapping("/find/all")
    public R<List<TemplateFlowchart>> findAll(@Validated TemplateFlowchart params) {
        String fileName = params.getFileName();
        if (fileName != null) {
            params.setFileName(fileName.toLowerCase());
        }
        return templateFlowchartService.findAll(params);
    }

    @GetMapping("/find/by/id")
    public R<TemplateFlowchart> findById(@Validated TemplateFlowchart params) {
        return templateFlowchartService.findById(params);
    }

    @PostMapping("/replicate")
    public R<Object> replicate(@RequestHeader(KeyVals.Token) String token,
                               @RequestBody @Validated TemplateFlowchart body) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        body.setCopies(body.getCopies() + 1);

        Flowchart flowchart = flowchartService.replicate(body.getFlowchart(), uid);
        return Objects.isNull(flowchart) ? R.error("导入模板失败！") : R.success("导入模板成功！");
    }

    @PutMapping("/renewal")
    public R<Object> renewal(@RequestBody @Validated TemplateFlowchart data) {
        boolean status = templateFlowchartService.renewal(data);
        return status ? R.success("更新成功！") : R.failed("更新失败！");
    }

    @PostMapping("/add/collect")
    public R<Object> addCollect(@RequestHeader(KeyVals.Token) String token,
                                @RequestBody @Validated TemplateFlowchart data) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        R<Object> status = collectFlowchartService.add(new CollectFlowchartVo(uid, data.getFlowchartId()));

        if (status.getCode() != 200) {
            return status;
        }

        data.setStars(data.getStars() + 1);
        boolean success = templateFlowchartService.renewal(data);

        return success ? R.success("收藏成功！") : R.failed("收藏失败！");
    }

}
