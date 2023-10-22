package com.bleuon.service;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.ConsumerModel;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/22
 */
public interface IAuthorityService {

    AdminModel findAdminAuthoritiesAll(String adminId);

    ConsumerModel findConsumerAuthoritiesAll(String consumerId);

}
