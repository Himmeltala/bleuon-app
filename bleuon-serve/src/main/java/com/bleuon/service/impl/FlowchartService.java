package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.BlueprintFlowchart;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
import com.bleuon.entity.vo.Sequence;
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
public class FlowchartService extends ServiceImpl<FlowchartMapper, Flowchart> implements IFlowchartService {

    private final FlowchartMapper flowchartMapper;
    private final BlueprintFlowchartService blueprintFlowchartService;

    @Transactional
    @Override
    public boolean upgrade(Flowchart body) {
        try {
            body.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = flowchartMapper.upgrade(body);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    public Flowchart findById(String flowchartId) {
        return query().eq("id", flowchartId).one();
    }

    @Transactional
    @Override
    public R<Flowchart> findIsShare(String id) {
        Flowchart flowchart = query()
                .eq("id", id)
                .eq("is_share", 1)
                .one();

        if (Objects.isNull(flowchart)) {
            return R.failed("该流程图未公开分享！", null);
        }

        if (DateUtil.isAfter(flowchart.getDeadShareDate())) {
            flowchart.setDeadShareDate(null);
            upgrade(flowchart);
            return R.failed("该流程图分享已过期！", null);
        }

        return R.success(flowchart);
    }

    @Override
    public List<Flowchart> findAllByCriteria(FlowchartCriteria criteria) {
        QueryWrapper<Flowchart> wrapper = new QueryWrapper<>();
        wrapper.eq("consumer_id", criteria.getCollectingCid());

        if (StringUtils.hasText(criteria.getFileName())) {
            wrapper.like("file_name", criteria.getFileName());
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
    public Flowchart add(String consumerId) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart flowchart = new Flowchart();
            flowchart.setId(uuid);
            flowchart.setConsumerId(consumerId);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            flowchart.setCreateDate(timestamp);
            flowchart.setModifyDate(timestamp);
            flowchart.setFileName("未命名的文件");

            save(flowchart);

            return flowchart;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public Flowchart replicate(Flowchart body, String consumerId) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart copyFlowchart = new Flowchart();
            copyFlowchart.setId(uuid);
            copyFlowchart.setConsumerId(consumerId);
            copyFlowchart.setCreateDate(new Timestamp(new Date().getTime()));
            copyFlowchart.setModifyDate(body.getModifyDate());
            copyFlowchart.setFileName(body.getFileName());
            copyFlowchart.setJson(body.getJson());
            copyFlowchart.setDataUri(body.getDataUri());
            copyFlowchart.setWidth(body.getWidth());
            copyFlowchart.setHeight(body.getHeight());
            copyFlowchart.setBgColor(body.getBgColor());
            copyFlowchart.setGridSize(body.getGridSize());
            copyFlowchart.setRouterDefault(body.getRouterDefault());
            copyFlowchart.setConnectorDefault(body.getConnectorDefault());

            save(copyFlowchart);

            return copyFlowchart;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public boolean deleteById(String flowchartId) {
        try {
            return removeById(flowchartId);
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public R<Object> release(BlueprintFlowchart body) {
        try {
            Flowchart flowchart = query().eq("id", body.getFlowchartId()).one();

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

            body.setId(UUID.randomUUID().toString());
            boolean added = blueprintFlowchartService.add(body);

            return added ? R.success("公开成功！") : R.failed("公开失败!");
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public R<Object> cancelRelease(String flowchartId) {
        try {
            Flowchart flowchart = new Flowchart();
            flowchart.setId(flowchartId);
            flowchart.setIsPublic(0);
            boolean status = upgrade(flowchart);

            if (!status) {
                return R.failed("取消发布失败！");
            }

            BlueprintFlowchart blueprintFlowchart = new BlueprintFlowchart();
            blueprintFlowchart.setFlowchartId(flowchartId);
            boolean canceled = blueprintFlowchartService.delete(blueprintFlowchart);

            if (!canceled) {
                return R.failed("取消发布失败！");
            }

            return R.success("取消发布成功!");
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }
}
