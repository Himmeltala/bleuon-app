package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartVo;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/update/one")
    public R<Void> updateOne(@RequestBody Flowchart data) {
        return service.updateOne(data);
    }

    @GetMapping("/query/one")
    public R<Flowchart> queryOne(@RequestParam String id) {
        return service.queryOne(id);
    }

    @PostMapping("/query/all")
    public R<List<Flowchart>> queryAll(@RequestHeader("Authorization") String token,
                                       @RequestBody(required = false) FlowchartVo vo) {
        Map<String, Object> params = new HashMap<>();
        Claims claims = JwtUtil.parseJwt(token);
        params.put("uid", claims.get("id"));
        if (!Objects.isNull(vo)) {
            params.put("fileName", vo.getFileName());
            params.put("collates", vo.getCollates());
        }
        return service.queryAll(params);
    }

    @PostMapping("/create/one")
    public R<Flowchart> createOne(@RequestHeader("Authorization") String token) {
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");
        return service.createOne(userId);
    }

    @PostMapping("/copy/one")
    public R<Flowchart> copyOne(@RequestHeader("Authorization") String token, @RequestBody Flowchart data) {
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");
        return service.copyOne(data, userId);
    }

    @DeleteMapping("/delete/one")
    public R<Void> deleteOne(@RequestParam String id) {
        return service.deleteOne(id);
    }

}
