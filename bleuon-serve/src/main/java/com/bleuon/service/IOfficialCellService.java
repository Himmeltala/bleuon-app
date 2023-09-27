package com.bleuon.service;

import com.bleuon.entity.OfficialCell;
import com.bleuon.utils.http.R;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/9/27
 */
public interface IOfficialCellService {

    R<List<OfficialCell>> queryAllCells(String type);

}
