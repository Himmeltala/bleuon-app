package com.bleuon.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Flowchart;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.FlowchartMapper;
import com.bleuon.service.IFlowchartService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

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
                    .set("is_public", data.getIsPublic() == null ? 0 : data.getIsPublic());

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
        return R.success(flowchart);
    }

}
