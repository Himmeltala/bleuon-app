package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.CollectingConsumerModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.DynamicModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.dto.ConsumerDTO;
import com.bleuon.entity.criterias.ConsumerCriteria;
import com.bleuon.entity.criterias.DynamicCriteria;
import com.bleuon.entity.criterias.FlowchartCriteria;
import com.bleuon.entity.criterias.Sequence;
import com.bleuon.service.impl.CollectingConsumerService;
import com.bleuon.service.impl.ConsumerService;
import com.bleuon.service.impl.DynamicService;
import com.bleuon.service.impl.FlowchartService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    @Operation(summary = "查询用户", parameters = {
            @Parameter(name = "id", description = "用户 UUID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1", in = ParameterIn.PATH)
    })
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/{id}")
    public R<ConsumerDTO> findById(@Validated @PathVariable @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！") String id) {
        ConsumerDTO exists = consumerService.findById(id);
        return !Objects.isNull(exists) ? R.success(exists) : R.failed("未查询到该用户！");
    }

    @Operation(summary = "更新用户")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade")
    public R<Object> upgrade(@Validated @RequestBody ConsumerModel model) {
        boolean status = consumerService.upgrade(model);
        return status ? R.success("更新资料成功！") : R.error("更新资料失败！");
    }

    @Operation(summary = "更新用户头像", parameters = {
            @Parameter(name = "id", description = "用户 UUID", example = "ea209fbb-8f0e-483e-be86-c3629ecbe6d1", in = ParameterIn.PATH)
    })
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade/avatar/{id}")
    public R<String> upgradeAvatar(@Validated @PathVariable @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！") String id,
                                   MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        String imgUrl = consumerService.upgradeAvatar(new ConsumerModel(id), file);

        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传头像成功！", imgUrl);
        } else {
            return R.error("上传头像失败！");
        }
    }

    @Operation(summary = "条件查询用户动态列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @PostMapping("/find/all/dynamic/criteria")
    public R<List<DynamicModel>> findAllDynamic(@Validated @RequestBody DynamicCriteria criteria) {
        List<DynamicModel> list = dynamicService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "更新动态")
    @PreAuthorize("hasAnyAuthority('sys:upgrade', 'sys:consumer:upgrade')")
    @PutMapping("/upgrade/dynamic")
    public R<Object> upgradeDynamic(@Validated @RequestBody DynamicModel model) {
        boolean status = dynamicService.upgrade(model);
        return status ? R.success("更新成功！") : R.error("更新失败！");
    }

    @Operation(summary = "通过 id 删除动态")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/dynamic")
    public R<Object> deleteDynamicById(DynamicModel model) {
        boolean status = dynamicService.deleteById(model);
        return status ? R.success("删除成功！") : R.error("删除失败！");
    }

    @Operation(summary = "新增单个动态")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/dynamic")
    public R<Object> addDynamic(@Validated @RequestBody DynamicModel model) {
        boolean status = dynamicService.add(model);
        return status ? R.success("添加成功！") : R.error("添加失败！");
    }

    @Operation(summary = "查询用户所有的流程图")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all/flowchart")
    public R<List<FlowchartModel>> findAllFlowchart(@Validated FlowchartCriteria criteria) {
        ArrayList<Sequence> sequences = new ArrayList<>();
        sequences.add(new Sequence(false, "modify_date"));
        criteria.setSequences(sequences);
        List<FlowchartModel> list = flowchartService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "根据条件查询用户的关注列表")
    @PreAuthorize("hasAnyAuthority('sys:find', 'sys:consumer:find')")
    @GetMapping("/find/all/collecting/criteria")
    public R<List<CollectingConsumerModel>> findAllCollectingConsumerByCriteria(@Validated ConsumerCriteria criteria) {
        List<CollectingConsumerModel> list = collectingConsumerService.findAllByCriteria(criteria);
        return R.success(list);
    }

    @Operation(summary = "新增单个关注用户", description = "需要传递 collectingCid、consumerId。")
    @PreAuthorize("hasAnyAuthority('sys:add', 'sys:consumer:add')")
    @PostMapping("/add/collecting")
    public R<Object> addCollecting(@Validated @RequestBody CollectingConsumerModel model) {
        ConsumerCriteria criteria = new ConsumerCriteria();
        criteria.setConsumerId(model.getConsumerId());
        criteria.setCollectingCid(model.getCollectingCid());
        CollectingConsumerModel exists = collectingConsumerService.findByCriteria(criteria);

        if (!Objects.isNull(exists)) {
            return R.error("您已经关注过了！");
        }

        boolean status = collectingConsumerService.add(model);
        return status ? R.success("关注成功！") : R.error("关注失败！");
    }

    @Operation(summary = "删除单个关注用户", description = "传递一个 ID 即可")
    @PreAuthorize("hasAnyAuthority('sys:delete', 'sys:consumer:delete')")
    @DeleteMapping("/delete/collecting")
    public R<Object> deleteCollecting(@Validated CollectingConsumerModel model) {
        boolean status = collectingConsumerService.delete(model);
        return status ? R.success("取消关注成功！") : R.error("取消关注失败！");
    }

}
