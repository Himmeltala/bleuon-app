package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.TemplateFlowchart;
import com.bleuon.entity.vo.FlowchartCriteria;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/9/29
 */
@Service("FlowchartService")
@RequiredArgsConstructor
public class FlowchartService extends ServiceImpl<FlowchartMapper, Flowchart> implements IFlowchartService {

    private final FlowchartMapper flowchartMapper;
    private final TemplateFlowchartService templateFlowchartService;

    @Transactional
    @Override
    public boolean renewal(Flowchart body) {
        try {
            Integer status = flowchartMapper.renewal(body);
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
    public R<Flowchart> exposeFindOne(String id) {
        Flowchart flowchart = query()
                .eq("id", id)
                .eq("is_share", 1)
                .one();

        if (Objects.isNull(flowchart)) {
            return R.failed("该流程图未公开分享！", null);
        }

        if (DateUtil.isAfter(flowchart.getDeadShareDate())) {
            flowchart.setDeadShareDate(null);
            renewal(flowchart);
            return R.failed("该流程图分享已过期！", null);
        }

        return R.success(flowchart);
    }

    @Override
    public List<Flowchart> findAllByCriteria(FlowchartCriteria criteria) {
        QueryWrapper<Flowchart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", criteria.getUid());

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

        List<FlowchartCriteria.Sequence> sequences = criteria.getSequences();
        if (sequences != null) {
            sequences.forEach(e -> wrapper.orderBy(true, e.getIsAsc(), e.getCol()));
        }

        return super.list(wrapper);
    }

    @Transactional
    @Override
    public Flowchart add(String uid) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart flowchart = new Flowchart();
            flowchart.setId(uuid);
            flowchart.setUserId(uid);
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
    public Flowchart replicate(Flowchart body, String uid) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart copyFlowchart = new Flowchart();
            copyFlowchart.setId(uuid);
            copyFlowchart.setUserId(uid);
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
    public boolean eraseById(String flowchartId) {
        try {
            return removeById(flowchartId);
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public R<Object> release(TemplateFlowchart body) {
        try {
            Flowchart flowchart = query().eq("id", body.getFlowchartId()).one();
            if (flowchart.getIsPublic() == 1) {
                return R.failed("已经公开过了！");
            }

            flowchart.setIsPublic(1);
            boolean status = renewal(flowchart);

            if (status) {
                body.setId(UUID.randomUUID().toString());
                boolean f = templateFlowchartService.add(body);
                if (f) {
                    return R.success("公开成功！");
                }
            }

            return R.failed("公开失败！");
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
            boolean status = renewal(flowchart);
            if (status) {
                TemplateFlowchart templateFlowchart = new TemplateFlowchart();
                templateFlowchart.setFlowchartId(flowchartId);
                boolean b = templateFlowchartService.erase(templateFlowchart);
                if (!b) {
                    return R.failed("取消发布失败！");
                }
            }
            return R.success("取消发布成功！");
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }
}
