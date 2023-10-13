package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.CollectingConsumer;
import com.bleuon.entity.Consumer;
import com.bleuon.entity.Dynamic;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.dto.ConsumerDto;
import com.bleuon.entity.vo.ConsumerCriteria;
import com.bleuon.entity.vo.DynamicCriteria;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.entity.vo.Sequence;
import com.bleuon.service.impl.CollectingConsumerService;
import com.bleuon.service.impl.ConsumerService;
import com.bleuon.service.impl.DynamicService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "用户")
@RequiredArgsConstructor
@RequestMappingPrefix("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final DynamicService dynamicService;
    private final FlowchartService flowchartService;
    private final CollectingConsumerService collectingConsumerService;

    @Operation(summary = "通过 id 查询用户数据")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/{id}")
    public R<ConsumerDto> findById(
            @Validated
            @PathVariable
            @Parameter(description = "用户 ID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1")
            @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
            String id
    ) {
        ConsumerDto exists = consumerService.findById(id);
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该用户！");
    }

    @Operation(summary = "更新用户数据")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade/{id}")
    public R<Object> upgrade(@Validated @RequestBody Consumer body) {
        boolean status = consumerService.upgrade(body);

        return status ? R.success("更新资料成功！") : R.error("更新资料失败！");
    }

    @Operation(summary = "更新用户头像")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade/avatar/{id}")
    public R<String> upgradeAvatar(
            @Validated
            @PathVariable
            @Parameter(description = "用户 ID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1")
            @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
            String id,
            @RequestParam MultipartFile file
    ) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        String imgUrl = consumerService.upgradeAvatar(new Consumer(id), file);

        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传头像成功！", imgUrl);
        } else {
            return R.error("上传头像失败！");
        }
    }

    @Operation(summary = "查询用户所有动态")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @PostMapping("/find/all/dynamic/criteria")
    public R<List<Dynamic>> findAllDynamic(@Validated @RequestBody DynamicCriteria criteria) {
        List<Dynamic> list = dynamicService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "更新单个动态")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade/dynamic")
    public R<Object> upgradeDynamic(@Validated @RequestBody Dynamic body) {
        boolean status = dynamicService.upgrade(body);
        return status ? R.success("更新成功！") : R.error("更新失败！");
    }

    @Operation(summary = "通过 id 删除动态")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/dynamic")
    public R<Object> deleteDynamicById(Dynamic params) {
        boolean status = dynamicService.deleteById(params);
        return status ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "新增单个动态")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/dynamic")
    public R<Object> addDynamic(@Validated @RequestBody Dynamic body) {
        boolean status = dynamicService.add(body);
        return status ? R.success("添加成功！") : R.error("添加失败！");
    }

    @Operation(summary = "查询用户所有的流程图")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all/flowchart")
    public R<List<Flowchart>> findAllFlowchart(@Validated FlowchartCriteria criteria) {
        ArrayList<Sequence> sequences = new ArrayList<>();
        sequences.add(new Sequence(false, "modify_date"));
        criteria.setSequences(sequences);
        List<Flowchart> list = flowchartService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "根据条件查询用户的关注列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all/collecting/criteria")
    public R<List<CollectingConsumer>> findAllCollectingConsumerByCriteria(@Validated ConsumerCriteria criteria) {
        List<CollectingConsumer> list = collectingConsumerService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "新增单个关注用户", description = "需要传递 collectingCid、consumerId。")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/collecting")
    public R<Object> addCollecting(@Validated @RequestBody CollectingConsumer body) {
        ConsumerCriteria criteria = new ConsumerCriteria();
        criteria.setConsumerId(body.getConsumerId());
        criteria.setCollectingCid(body.getCollectingCid());
        CollectingConsumer exists = collectingConsumerService.findByCriteria(criteria);

        if (!Objects.isNull(exists)) {
            return R.error("您已经关注过了！");
        }

        boolean status = collectingConsumerService.add(body);
        return status ? R.success("关注成功！") : R.error("关注失败！");
    }

    @Operation(summary = "删除单个关注用户", description = "传递一个 ID 即可")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/collecting")
    public R<Object> deleteCollecting(@Validated CollectingConsumer params) {
        boolean status = collectingConsumerService.delete(params);
        return status ? R.success("取消关注成功！") : R.error("取消关注失败！");
    }

}
