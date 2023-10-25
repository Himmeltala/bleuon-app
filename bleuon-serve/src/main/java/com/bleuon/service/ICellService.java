package com.bleuon.service;

import com.bleuon.entity.CellModel;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/27
 */
public interface ICellService {

    List<CellModel> findAllByCriteria(CellModel model);

}
