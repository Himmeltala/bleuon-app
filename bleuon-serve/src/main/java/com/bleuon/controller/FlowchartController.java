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

    private final FlowchartService service;
    private final CollectFlowchartService collectService;

    @PostMapping("/update/one")
    public R<Void> updateOne(@RequestBody Flowchart data) {
        return service.updateOne(data);
    }

    @GetMapping("/find/one")
    public R<Flowchart> findOne(@RequestParam String id) {
        return service.findOne(id);
    }

    @PostMapping("/find/all")
    public R<List<Flowchart>> findAll(@RequestHeader("Authorization") String token,
                                      @RequestBody(required = false) FlowchartCondition condition) {

        if (Objects.isNull(condition)) condition = new FlowchartCondition();
        Claims claims = JwtUtil.parseJwt(token);
        condition.setUid((String) claims.get("id"));

        return service.findAll(condition);
    }

    @PostMapping("/create/one")
    public R<Flowchart> createOne(@RequestHeader("Authorization") String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");
        return service.createOne(userId);
    }

    @PostMapping("/clone/one")
    public R<Flowchart> cloneOne(@RequestHeader("Authorization") String token, @RequestBody Flowchart data) {
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");
        return service.cloneOne(data, userId);
    }

    @DeleteMapping("/delete/one")
    public R<Void> deleteOne(@RequestParam String id) {
        return service.deleteOne(id);
    }

    @GetMapping("/collect/find/all")
    public R<List<CollectFlowchart>> findAllCollect(@RequestParam String uid) {
        return collectService.findAll(uid);
    }

}
