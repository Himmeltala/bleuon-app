package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.FlowchartCondition;
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
@Service
@RequiredArgsConstructor
public class FlowchartService extends ServiceImpl<FlowchartMapper, Flowchart> implements IFlowchartService {

    @Override
    @Transactional
    public boolean updateOne(Flowchart data) {
        try {
            UpdateWrapper<Flowchart> updateWrapper = new UpdateWrapper<>();

            updateWrapper
                    .eq("id", data.getId())
                    .set("file_name", data.getFileName())
                    .set("json", data.getJson())
                    .set("modify_date", new Timestamp(new Date().getTime()))
                    .set("width", data.getWidth())
                    .set("height", data.getHeight())
                    .set("data_uri", data.getDataUri() == null ? "" : data.getDataUri())
                    .set("is_public", data.getIsPublic() == null ? 0 : data.getIsPublic())
                    .set("dead_share_date", data.getDeadShareDate());

            return update(data, updateWrapper);
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    public Flowchart findOne(String id) {
        return query().eq("id", id).one();
    }

    @Override
    @Transactional
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
            updateOne(flowchart);
            return R.failed("该流程图分享已过期！", null);
        }

        return R.success(flowchart);
    }

    @Override
    public List<Flowchart> findAll(FlowchartCondition condition) {
        QueryWrapper<Flowchart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", condition.getUid());

        if (StringUtils.hasText(condition.getFileName())) {
            wrapper.like("file_name", condition.getFileName());
        }

        if (condition.getIsShare() != null) {
            wrapper.eq("is_share", condition.getIsShare());
        }

        if (condition.getIsPublic() != null) {
            wrapper.eq("is_public", condition.getIsPublic());
        }

        if (condition.getIsLegal() != null) {
            wrapper.eq("is_legal", condition.getIsLegal());
        }

        List<FlowchartCondition.Collate> collates = condition.getCollates();
        if (collates != null) {
            collates.forEach(e -> wrapper.orderBy(true, e.getIsAsc(), e.getCol()));
        }

        return super.list(wrapper);
    }

    @Override
    @Transactional
    public Flowchart createOne(String userId) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart flowchart = new Flowchart();
            flowchart.setId(uuid);
            flowchart.setUserId(userId);
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

    @Override
    @Transactional
    public Flowchart cloneOne(Flowchart data, String uid) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart copyFlowchart = new Flowchart();
            copyFlowchart.setId(uuid);
            copyFlowchart.setUserId(uid);
            copyFlowchart.setCreateDate(new Timestamp(new Date().getTime()));
            copyFlowchart.setModifyDate(data.getModifyDate());
            copyFlowchart.setFileName(data.getFileName());
            copyFlowchart.setJson(data.getJson());
            copyFlowchart.setDataUri(data.getDataUri());
            copyFlowchart.setWidth(data.getWidth());
            copyFlowchart.setHeight(data.getHeight());
            copyFlowchart.setBgColor(data.getBgColor());
            copyFlowchart.setGridSize(data.getGridSize());
            copyFlowchart.setRouterDefault(data.getRouterDefault());
            copyFlowchart.setConnectorDefault(data.getConnectorDefault());

            save(copyFlowchart);

            return copyFlowchart;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public boolean deleteOne(String id) {
        try {
            return removeById(id);
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
