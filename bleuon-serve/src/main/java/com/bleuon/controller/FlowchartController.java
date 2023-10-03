package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.dto.CollectFlowchart;
import com.bleuon.entity.vo.FlowchartCondition;
import com.bleuon.service.impl.CollectFlowchartService;
import com.bleuon.service.impl.FlowchartService;
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
 * @date: 2023/9/29
 */
@Validated
@RequiredArgsConstructor
@RequestMappingPrefix("/flowchart")
public class FlowchartController {

    private final FlowchartService flowchart;
    private final CollectFlowchartService collect;

    @PostMapping("/update/one")
    public R<Void> updateOne(@RequestBody Flowchart data) {
        boolean status = flowchart.updateOne(data);
        if (status) {
            return R.success("更新流程图成功！");
        } else {
            return R.failed("更新流程图失败！");
        }
    }

    @GetMapping("/find/one")
    public R<Flowchart> findOne(@RequestParam String id) {
        Flowchart result = flowchart.findOne(id);
        if (Objects.isNull(result)) return R.failed("该流程图不存在！", null);
        return R.success(result);
    }

    @PostMapping("/find/all")
    public R<List<Flowchart>> findAll(@RequestHeader("Authorization") String token,
                                      @RequestBody(required = false) FlowchartCondition condition
    ) {
        if (Objects.isNull(condition)) condition = new FlowchartCondition();
        Claims claims = JwtUtil.parseJwt(token);
        condition.setUid((String) claims.get("id"));

        List<Flowchart> list = flowchart.findAll(condition);
        if (list.isEmpty()) return R.failed("没有查询到流程图！", null);
        return R.success(list);
    }

    @PostMapping("/create/one")
    public R<Flowchart> createOne(@RequestHeader("Authorization") String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");

        Flowchart flowchart = this.flowchart.createOne(userId);
        if (!Objects.isNull(flowchart)) {
            return R.success("创建流程图成功！", flowchart);
        }
        return R.failed("创建流程图失败！", null);
    }

    @PostMapping("/clone/one")
    public R<Flowchart> cloneOne(@RequestHeader("Authorization") String token,
                                 @RequestBody Flowchart data
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");

        Flowchart flowchart = this.flowchart.cloneOne(data, userId);
        if (!Objects.isNull(flowchart)) {
            return R.success("复制流程图成功！", flowchart);
        }
        return R.failed("复制流程图失败！", null);
    }

    @DeleteMapping("/delete/one")
    public R<Void> deleteOne(@RequestParam String id) {
        boolean status = flowchart.deleteOne(id);
        if (status) return R.success("删除流程图成功！");
        return R.failed("删除流程图失败！");
    }

    @GetMapping("/find/all/collect")
    public R<List<CollectFlowchart>> findAllCollect(@RequestParam String uid) {
        List<CollectFlowchart> list = collect.findAll(uid);
        if (list != null) return R.success(list);
        return R.failed("没有查询到数据！", null);
    }

    @DeleteMapping("/delete/one/collect")
    public R<Void> deleteOneCollect(@RequestParam String id) {
        boolean status = collect.deleteOne(id);
        if (status) return R.success("删除收藏的流程图成功！");
        return R.failed("删除收藏的流程图失败！");
    }

}
