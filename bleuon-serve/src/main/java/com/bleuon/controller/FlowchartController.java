package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.entity.dto.CollectFlowchartDto;
import com.bleuon.entity.vo.CollectFlowchartVo;
import com.bleuon.entity.vo.FlowchartCondition;
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

    @PostMapping("/update/one")
    public R<Object> updateOne(@RequestBody @Validated Flowchart data) {
        boolean status = flowchartService.updateOne(data);
        if (status) {
            return R.success("更新流程图成功！");
        } else {
            return R.failed("更新流程图失败！");
        }
    }

    @GetMapping("/find/one")
    public R<Flowchart> findOne(@RequestParam String id) {
        Flowchart result = flowchartService.findOne(id);
        if (Objects.isNull(result)) return R.failed("该流程图不存在！", null);
        return R.success(result);
    }

    @PostMapping("/find/all")
    public R<List<Flowchart>> findAll(@RequestHeader(KeyVals.Token) String token,
                                      @RequestBody(required = false) FlowchartCondition condition
    ) {
        if (Objects.isNull(condition)) condition = new FlowchartCondition();
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        condition.setUid(uid);

        List<Flowchart> list = flowchartService.findAll(condition);
        if (list.isEmpty()) return R.failed("没有查询到流程图！", null);
        return R.success(list);
    }

    @PostMapping("/create/one")
    public R<Flowchart> createOne(@RequestHeader(KeyVals.Token) String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        Flowchart flowchart = this.flowchartService.createOne(uid);
        if (!Objects.isNull(flowchart)) {
            return R.success("创建流程图成功！", flowchart);
        }
        return R.failed("创建流程图失败！", null);
    }

    @PostMapping("/clone/one")
    public R<Flowchart> cloneOne(@RequestHeader(KeyVals.Token) String token,
                                 @RequestBody @Validated Flowchart data
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        Flowchart flowchart = this.flowchartService.cloneOne(data, uid);
        if (!Objects.isNull(flowchart)) {
            return R.success("复制流程图成功！", flowchart);
        }
        return R.failed("复制流程图失败！", null);
    }

    @DeleteMapping("/delete/one")
    public R<Object> deleteOne(@RequestParam String id) {
        boolean status = flowchartService.deleteOne(id);
        if (status) return R.success("删除流程图成功！");
        return R.failed("删除流程图失败！");
    }

    @GetMapping("/find/all/collect")
    public R<List<CollectFlowchartDto>> findAllCollect(@RequestHeader(KeyVals.Token) String token,
                                                       @ModelAttribute FlowchartCondition condition
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        condition.setUid(uid);

        List<CollectFlowchartDto> list = collectFlowchartService.findAll(condition);
        if (list != null) return R.success(list);
        return R.failed("没有收藏流程图！", null);
    }

    @DeleteMapping("/delete/one/collect")
    public R<Object> deleteOneCollect(@RequestHeader(KeyVals.Token) String token,
                                      @ModelAttribute CollectFlowchartVo data
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        data.setCollectUid(uid);
        boolean status = collectFlowchartService.deleteOne(data);
        if (status) return R.success("删除收藏的流程图成功！");
        return R.failed("删除收藏的流程图失败！");
    }

    @PostMapping("/add/one/collect")
    public R<Object> addOneCollect(@RequestHeader(KeyVals.Token) String token,
                                   @RequestBody CollectFlowchartVo data
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");
        data.setCollectUid(uid);
        return collectFlowchartService.addOne(data);
    }

    @PostMapping("/release/one")
    public R<Object> releaseOne(@RequestBody @Validated TemplateFlowchart data) {
        return flowchartService.releaseOne(data);
    }

    @DeleteMapping("/cancel/release/one")
    public R<Object> cancelReleaseOne(
            @RequestParam
            @Pattern(regexp = ValidPattern.UUID, message = "不是一个合法的 UUID")
            String flowchartId
    ) {
        return flowchartService.cancelReleaseOne(flowchartId);
    }

}
