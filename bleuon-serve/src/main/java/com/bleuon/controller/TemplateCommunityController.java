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

    private final TemplateFlowchartService service;
    private final FlowchartService flowchartService;
    private final CollectFlowchartService collectService;

    @GetMapping("/find/all")
    public R<List<TemplateFlowchart>> findAll(@Validated TemplateFlowchart data) {
        return service.findAll(data);
    }

    @GetMapping("/find/one")
    public R<TemplateFlowchart> findOne(@Validated TemplateFlowchart data) {
        return service.find(data);
    }

    @PostMapping("/clone/one")
    public R<Object> cloneOne(@RequestHeader(KeyVals.Token) String token,
                              @RequestBody @Validated TemplateFlowchart data
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        // 加一次导入次数
        Integer copies = data.getCopies();
        data.setCopies(copies + 1);

        Flowchart flowchart = flowchartService.replicate(data.getFlowchart(), uid);
        if (Objects.isNull(flowchart)) return R.error("导入模板失败！");
        return R.success("导入模板成功！");
    }

    @PutMapping("/update/one")
    public R<Object> updateOne(@RequestBody @Validated TemplateFlowchart data) {
        boolean status = service.renewal(data);
        if (status) return R.success("更新成功！");
        return R.failed("更新失败！");
    }

    @PostMapping("/collect/one")
    public R<Object> collectOne(@RequestHeader(KeyVals.Token) String token,
                                @RequestBody @Validated TemplateFlowchart data
    ) {
        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        CollectFlowchartVo vo = new CollectFlowchartVo();
        vo.setCollectUid(uid);
        vo.setFlowchartId(data.getFlowchartId());
        R<Object> status = collectService.add(vo);
        Integer code = status.getCode();
        if (code == 200) {
            data.setStars(data.getStars() + 1);
            boolean s = service.renewal(data);
            if (s) return R.success("收藏成功！");
            return R.failed("收藏失败！");
        } else {
            return status;
        }
    }

}
