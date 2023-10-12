package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Cell;
import com.bleuon.mapper.CellMapper;
import com.bleuon.service.ICellService;
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
@Service
@RequiredArgsConstructor
public class CellService extends ServiceImpl<CellMapper, Cell> implements ICellService {

    @Override
    public R<List<Cell>> findAllByCriteria(Cell params) {
        List<Cell> list = this.query().eq("type", params.getType()).list();
        return R.success(list);
    }

}
