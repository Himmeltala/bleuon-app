package com.bleuon.service;

import com.bleuon.entity.CellModel;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/27
 */
public interface ICellService {

    R<List<CellModel>> findAllByCriteria(CellModel model);

}
