package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.service.impl.CollectFlowchartService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
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
    private final CollectFlowchartService collectFlowchartService;

    @PutMapping("/renewal")
    public R<Object> renewal(@RequestBody @Validated Flowchart body) {
        boolean status = flowchartService.renewal(body);
        if (status) {
            return R.success("更新流程图成功！");
        } else {
            return R.failed("更新流程图失败！");
        }
    }

    @GetMapping("/find/by/id")
    public R<Flowchart> findById(@RequestParam String id) {
        Flowchart result = flowchartService.findById(id);
        if (Objects.isNull(result)) return R.failed("该流程图不存在！", null);
        return R.success(result);
    }

    @PostMapping("/find/all/by/criteria")
    public R<List<Flowchart>> findAllByCriteria(@RequestHeader(KeyVals.Token) String token,
                                                @RequestBody FlowchartCriteria criteria
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        criteria.setUid(uid);

        List<Flowchart> list = flowchartService.findAllByCriteria(criteria);
        if (list.isEmpty()) return R.failed("没有查询到流程图！", null);
        return R.success(list);
    }

    @PostMapping("/add")
    public R<Flowchart> add(@RequestHeader(KeyVals.Token) String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        Flowchart flowchart = this.flowchartService.add(uid);
        if (!Objects.isNull(flowchart)) {
            return R.success("创建流程图成功！", flowchart);
        }
        return R.failed("创建流程图失败！", null);
    }

    @PostMapping("/replicate")
    public R<Flowchart> replicate(@RequestHeader(KeyVals.Token) String token,
                                  @RequestBody @Validated Flowchart body
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        Flowchart flowchart = this.flowchartService.replicate(body, uid);
        if (!Objects.isNull(flowchart)) {
            return R.success("复制流程图成功！", flowchart);
        }
        return R.failed("复制流程图失败！", null);
    }

    @DeleteMapping("/erase/by/id")
    public R<Object> eraseById(@RequestParam String id) {
        boolean status = flowchartService.eraseById(id);
        if (status) return R.success("删除流程图成功！");
        return R.failed("删除流程图失败！");
    }

    @GetMapping("/find/all/collect/by/criteria")
    public R<List<CollectFlowchartDto>> findAllCollectByCriteria(
            @RequestHeader(KeyVals.Token) String token,
            FlowchartCriteria criteria
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        criteria.setUid(uid);

        List<CollectFlowchartDto> list = collectFlowchartService.findAllCollectByCriteria(criteria);
        if (list != null) return R.success(list);
        return R.failed("没有收藏流程图！", null);
    }

    @DeleteMapping("/erase/collect")
    public R<Object> eraseCollect(
            @RequestHeader(KeyVals.Token) String token,
            @Validated CollectFlowchartVo params
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        params.setCollectUid(uid);

        boolean status = collectFlowchartService.erase(params);
        if (status) return R.success("删除收藏的流程图成功！");
        return R.failed("删除收藏的流程图失败！");
    }

    @PostMapping("/add/collect")
    public R<Object> addCollect(
            @RequestHeader(KeyVals.Token) String token,
            @RequestBody @Validated CollectFlowchartVo body
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        body.setCollectUid(uid);
        return collectFlowchartService.add(body);
    }

    @PostMapping("/release")
    public R<Object> release(@RequestBody @Validated TemplateFlowchart body) {
        return flowchartService.release(body);
    }

    @DeleteMapping("/cancel/release")
    public R<Object> cancelRelease(
            @RequestParam
            @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
            String flowchartId
    ) {
        return flowchartService.cancelRelease(flowchartId);
    }

}
