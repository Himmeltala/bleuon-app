package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.OfficialCell;
import com.bleuon.mapper.OfficialCellMapper;
import com.bleuon.service.IOfficialCellService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/9/27
 */
@Service("OfficialCellService")
@RequiredArgsConstructor
public class OfficialCellService extends ServiceImpl<OfficialCellMapper, OfficialCell> implements IOfficialCellService {

    @Override
    public R<List<OfficialCell>> queryAllCells(String type) {
        List<OfficialCell> list = this.query().eq("type", type).list();
        return R.success(list);
    }

}
