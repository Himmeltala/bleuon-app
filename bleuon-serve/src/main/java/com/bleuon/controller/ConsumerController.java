package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.Consumer;
import com.bleuon.entity.Dynamic;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.dto.ConsumerDto;
import com.bleuon.entity.vo.DynamicCriteria;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.entity.vo.Sequence;
import com.bleuon.service.impl.ConsumerService;
import com.bleuon.service.impl.DynamicService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/6
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final DynamicService dynamicService;
    private final FlowchartService flowchartService;

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/by/id")
    public R<ConsumerDto> findById(
            @RequestHeader(KeyVals.Token) String token,
            @Validated
            @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
            @RequestParam(required = false)
            String id
    ) {
        ConsumerDto exists;
        if (!StringUtils.hasText(id)) {
            Claims claims = JwtUtil.parseJwt(token);
            exists = consumerService.findById((String) claims.get("id"));
        } else {
            exists = consumerService.findById(id);
        }
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该用户！");
    }

    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PostMapping("/upgrade")
    public R<Object> upgrade(@RequestHeader(KeyVals.Token) String token, @RequestBody @Validated Consumer body) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        body.setId(consumerId);
        boolean status = consumerService.upgrade(body);

        return status ? R.success("更新资料成功！") : R.error("更新资料失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PostMapping("/upgrade/avatar")
    public R<String> upgradeAvatar(@RequestHeader(KeyVals.Token) String token,
                                   @RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        Consumer consumer = new Consumer();
        consumer.setId(consumerId);

        String imgUrl = consumerService.upgradeAvatar(consumer, file);

        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传头像成功！", imgUrl);
        }

        return R.error("上传头像失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @PostMapping("/find/all/dynamic/by/criteria")
    public R<List<Dynamic>> findAllDynamic(@RequestBody DynamicCriteria criteria) {
        List<Dynamic> list = dynamicService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PostMapping("/upgrade/dynamic")
    public R<Object> upgradeDynamic(@RequestBody Dynamic body) {
        boolean status = dynamicService.upgrade(body);
        return status ? R.success("更新成功！") : R.error("更新失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/dynamic")
    public R<Object> deleteDynamicById(Dynamic params) {
        boolean status = dynamicService.deleteById(params);
        return status ? R.success("删除成功！") : R.error("删除失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/dynamic")
    public R<Object> addDynamic(@RequestHeader(KeyVals.Token) String token, @RequestBody Dynamic body) {
        Claims claims = JwtUtil.parseJwt(token);
        String consumerId = (String) claims.get("id");
        body.setConsumerId(consumerId);
        boolean status = dynamicService.add(body);
        return status ? R.success("添加成功！") : R.error("添加失败！");
    }

    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all/flowchart")
    public R<List<Flowchart>> findAllFlowchart(FlowchartCriteria criteria) {
        ArrayList<Sequence> sequences = new ArrayList<>();
        sequences.add(new Sequence(false, "modify_date"));
        criteria.setSequences(sequences);
        List<Flowchart> list = flowchartService.findAllByCriteria(criteria);
        return R.success(list);
    }

}
