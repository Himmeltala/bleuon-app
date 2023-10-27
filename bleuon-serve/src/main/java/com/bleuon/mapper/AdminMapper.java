package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.criterias.AdminCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.mapper
 * @author: zheng
 * @date: 2023/10/22
 */
@Mapper
public interface AdminMapper extends BaseMapper<AdminModel> {

    AdminModel findByUsernameOrPhoneOrEmail(AdminModel model);

    AdminModel findBy(AdminModel model);

    List<AdminModel> findListBy(AdminCriteria criteria);

}
