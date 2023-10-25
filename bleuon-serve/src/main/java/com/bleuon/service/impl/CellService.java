package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.CellModel;
import com.bleuon.mapper.CellMapper;
import com.bleuon.service.ICellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/9/27
 */
@Service
@RequiredArgsConstructor
public class CellService extends ServiceImpl<CellMapper, CellModel> implements ICellService {

    @Override
    public List<CellModel> findAllByCriteria(CellModel model) {
        return this.query().eq("type", model.getType()).list();
    }

}
