package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Flowchart;
import com.bleuon.entity.vo.Collate;
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

    @Override
    @Transactional
    public R<Void> updateOne(Flowchart data) {
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

            boolean f = update(data, updateWrapper);
            if (f) {
                return R.success("更新流程图成功！");
            } else {
                return R.failed("更新流程图失败！");
            }
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    public R<Flowchart> queryOne(String id) {
        Flowchart flowchart = query().eq("id", id).one();

        if (Objects.isNull(flowchart)) {
            return R.failed("查询失败，没有该流程图！", null);
        }

        return R.success(flowchart);
    }

    @Override
    @Transactional
    public R<Flowchart> exposeQueryOne(String id) {
        Flowchart flowchart = query()
                .eq("id", id)
                .eq("is_public", 1)
                .one();

        if (Objects.isNull(flowchart)) {
            return R.failed("该流程图不是公开的！", null);
        }

        boolean isAfter = DateUtil.isAfter(flowchart.getDeadShareDate());
        if (isAfter) {
            flowchart.setDeadShareDate(null);
            updateOne(flowchart);
            return R.failed("该分享的流程图已经过期！", null);
        }

        return R.success(flowchart);
    }

    @Override
    public R<List<Flowchart>> queryAll(Map<String, Object> params) {
        String uid = (String) params.get("uid");
        String fileName = (String) params.get("fileName");
        List<Collate> collates = (List<Collate>) params.get("collates");

        QueryWrapper<Flowchart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);

        if (StringUtils.hasText(fileName)) {
            wrapper.like("file_name", fileName);
        }

        if (collates != null) {
            collates.forEach(e -> wrapper.orderBy(true, e.getIsAsc(), e.getCol()));
        }

        List<Flowchart> list = super.list(wrapper);

        if (list.isEmpty()) {
            return R.failed("没有查询到流程图！", null);
        }

        return R.success(list);
    }

    @Override
    @Transactional
    public R<Flowchart> createOne(String userId) {
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
            boolean f = save(flowchart);
            if (f) {
                return R.success("创建流程图成功！", flowchart);
            } else {
                return R.failed("创建流程图失败！", null);
            }
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public R<Flowchart> copyOne(Flowchart data, String userId) {
        try {
            String uuid = UUID.randomUUID().toString();
            // 创建表
            Flowchart copyFlowchart = new Flowchart();
            copyFlowchart.setId(uuid);
            copyFlowchart.setUserId(userId);
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
            copyFlowchart.setIsPublic(data.getIsPublic());
            boolean f = save(copyFlowchart);
            if (f) {
                return R.success("复制流程图成功！", copyFlowchart);
            } else {
                return R.failed("复制流程图失败！", null);
            }
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Override
    @Transactional
    public R<Void> deleteOne(String id) {
        try {
            boolean f = removeById(id);
            if (f) {
                return R.success("删除流程图成功！");
            } else {
                return R.failed("删除流程图失败！");
            }
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
