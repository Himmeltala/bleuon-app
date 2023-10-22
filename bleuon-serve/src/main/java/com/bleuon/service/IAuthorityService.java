package com.bleuon.service;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.criterias.AuthorityCriteria;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/22
 */
public interface IAuthorityService {

    AdminModel findAdmin(String adminId);

    ConsumerModel findConsumer(String consumerId);

    PageInfo<AdminModel> findAllAdmin(AuthorityCriteria criteria);

    PageInfo<ConsumerModel> findAllConsumer(AuthorityCriteria criteria);

}
