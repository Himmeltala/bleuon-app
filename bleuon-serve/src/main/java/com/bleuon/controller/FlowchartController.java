package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.Flowchart;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查询一个流程图，必须要登录之后才可以查询，也就是私密的流程图
     *
     * @param id 流程图 ID
     */
    @GetMapping("/query/one")
    public R<Flowchart> queryOne(@RequestParam String id) {
        return service.queryOne(id);
    }

    @GetMapping("/query/all")
    public R<List<Flowchart>> queryAll(@RequestHeader("Authorization") String token,
                                       @RequestParam String[] cols,
                                       @RequestParam String type) {
        System.out.println(type);
        Claims claims = JwtUtil.parseJwt(token);
        String userId = (String) claims.get("id");
        return service.queryAll(userId, type, cols);
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
