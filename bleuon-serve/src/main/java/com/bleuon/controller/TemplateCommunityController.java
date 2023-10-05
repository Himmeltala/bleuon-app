package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.service.impl.TemplateFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/5
 */
@Validated
@RequiredArgsConstructor
@RequestMappingPrefix("/community/template")
public class TemplateCommunityController {

    private final TemplateFlowchartService service;

    @GetMapping("/find/all")
    public R<List<TemplateFlowchart>> findAll(TemplateFlowchart data) {
        return service.findAll(data);
    }

    @GetMapping("find/one")
    public R<TemplateFlowchart> findOne(TemplateFlowchart data) {
        return service.findOne(data);
    }

}
