package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.BlueprintFlowchartModel;
import com.bleuon.entity.FlowchartModel;
import com.bleuon.entity.criterias.FlowchartCriteria;
import com.bleuon.entity.criterias.Sequence;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.FlowchartMapper;
import com.bleuon.service.IFlowchartService;
import com.bleuon.utils.DateUtil;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/9/29
 */
@Service
@RequiredArgsConstructor
public class FlowchartService extends ServiceImpl<FlowchartMapper, FlowchartModel> implements IFlowchartService {

    private final FlowchartMapper flowchartMapper;
    private final BlueprintFlowchartService blueprintFlowchartService;

    @Transactional
    @Override
    public boolean upgrade(FlowchartModel model) {
        try {
            model.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = flowchartMapper.upgrade(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public FlowchartModel findById(String flowchartId) {
        FlowchartModel result = query().eq("id", flowchartId).one();
        if (DateUtil.isAfter(result.getDeadShareDate())) {
            result.setIsShare(0);
            upgrade(result);
        }
        return result;
    }

    @Transactional
    @Override
    public R<FlowchartModel> findIsShare(String id) {
        FlowchartModel flowchart = query()
                .eq("id", id)
                .eq("is_share", 1)
                .one();

        if (Objects.isNull(flowchart)) {
            return R.error("该流程图未公开分享！");
        }

        if (DateUtil.isAfter(flowchart.getDeadShareDate())) {
            flowchart.setIsShare(0);
            upgrade(flowchart);
            return R.error("该流程图分享已过期！");
        }

        return R.success(flowchart);
    }

    @Override
    public List<FlowchartModel> findAllByCriteria(FlowchartCriteria criteria) {
        QueryWrapper<FlowchartModel> wrapper = new QueryWrapper<>();
        wrapper.eq("consumer_id", criteria.getCollectorId());

        if (StringUtils.hasText(criteria.getFilename())) {
            wrapper.like("filename", criteria.getFilename());
        }

        if (criteria.getIsShare() != null) {
            wrapper.eq("is_share", criteria.getIsShare());
        }

        if (criteria.getIsPublic() != null) {
            wrapper.eq("is_public", criteria.getIsPublic());
        }

        if (criteria.getIsLegal() != null) {
            wrapper.eq("is_legal", criteria.getIsLegal());
        }

        List<Sequence> sequences = criteria.getSequences();
        if (sequences != null) {
            sequences.forEach(e -> wrapper.orderBy(true, e.getIsAsc(), e.getCol()));
        }

        return super.list(wrapper);
    }

    @Transactional
    @Override
    public FlowchartModel add(String consumerId) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            FlowchartModel flowchart = new FlowchartModel();
            flowchart.setId(uuid);
            flowchart.setConsumerId(consumerId);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            flowchart.setCreateDate(timestamp);
            flowchart.setModifyDate(timestamp);
            flowchart.setFilename("未命名的文件");

            save(flowchart);

            return flowchart;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public FlowchartModel replicate(FlowchartModel model) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            FlowchartModel rep = new FlowchartModel();
            rep.setId(uuid);
            rep.setConsumerId(model.getConsumerId());
            rep.setModifyDate(model.getModifyDate());
            rep.setFilename(model.getFilename());
            rep.setJson(model.getJson());
            rep.setDataUri(model.getDataUri());
            rep.setWidth(model.getWidth());
            rep.setHeight(model.getHeight());
            rep.setBgColor(model.getBgColor());
            rep.setGridSize(model.getGridSize());
            rep.setRouterDefault(model.getRouterDefault());
            rep.setConnectorDefault(model.getConnectorDefault());

            boolean success = save(rep);
            if (!success) return null;

            return rep;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteById(String flowchartId) {
        try {
            return removeById(flowchartId);
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public R<Object> release(BlueprintFlowchartModel model) {
        try {
            FlowchartModel flowchart = query().eq("id", model.getFlowchartId()).one();

            if (flowchart.getIsPublic() == 1) {
                return R.failed("已经公开过了！");
            }

            flowchart.setIsShare(1);
            flowchart.setIsPublic(1);
            flowchart.setIsBlueprint(1);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2099, Calendar.DECEMBER, 31);
            flowchart.setDeadShareDate(new Timestamp(calendar.getTimeInMillis()));
            boolean status = upgrade(flowchart);

            if (!status) {
                return R.failed("公开失败!");
            }

            model.setId(UUID.randomUUID().toString());
            boolean added = blueprintFlowchartService.add(model);

            return added ? R.success("公开成功！") : R.failed("公开失败!");
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public R<Object> cancelRelease(String flowchartId) {
        try {
            FlowchartModel flowchart = new FlowchartModel();
            flowchart.setId(flowchartId);
            flowchart.setIsBlueprint(0);
            flowchart.setIsPublic(0);
            flowchart.setIsShare(0);
            boolean status = upgrade(flowchart);

            if (!status) {
                return R.failed("取消发布失败！");
            }

            BlueprintFlowchartModel blueprintFlowchart = new BlueprintFlowchartModel();
            blueprintFlowchart.setFlowchartId(flowchartId);
            boolean canceled = blueprintFlowchartService.drop(blueprintFlowchart);

            if (!canceled) {
                return R.failed("取消发布失败！");
            }

            return R.success("取消发布成功!");
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
