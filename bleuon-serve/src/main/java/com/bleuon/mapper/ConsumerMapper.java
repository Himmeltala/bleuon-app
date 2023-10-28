package com.bleuon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bleuon.entity.ConsumerModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 有关用户的 CRUD 功能
 *
 * @author zheng
 */
@Mapper
public interface ConsumerMapper extends BaseMapper<ConsumerModel> {

    Integer upgrade(ConsumerModel model);

    ConsumerModel findBy(ConsumerModel model);

    ConsumerModel findByUsernameOrPhoneOrEmail(ConsumerModel model);

}
