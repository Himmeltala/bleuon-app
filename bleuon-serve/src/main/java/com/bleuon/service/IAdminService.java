package com.bleuon.service;

import com.bleuon.entity.AdminModel;
import com.bleuon.entity.criterias.AdminCriteria;
import com.github.pagehelper.PageInfo;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/22
 */
public interface IAdminService {

    AdminModel findByUsernameOrPhoneOrEmailAndPwd(AdminModel model);

    AdminModel findBy(AdminModel model);

    PageInfo<AdminModel> findListBy(AdminCriteria criteria);

}
