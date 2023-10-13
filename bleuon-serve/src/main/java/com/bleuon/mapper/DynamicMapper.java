package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.DynamicModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/10
 */
@Mapper
public interface DynamicMapper extends BaseMapper<DynamicModel> {

    Integer upgrade(DynamicModel model);

}
