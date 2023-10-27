package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.criterias.AdminCriteria;
import com.bleuon.mapper.AdminMapper;
import com.bleuon.service.IAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/22
 */
@Service
@RequiredArgsConstructor
public class AdminService extends ServiceImpl<AdminMapper, AdminModel> implements IAdminService {

    private final AdminMapper adminMapper;

    @Override
    public AdminModel findByUsernameOrPhoneOrEmail(AdminModel model) {
        return adminMapper.findByUsernameOrPhoneOrEmail(model);
    }

    @Override
    public AdminModel findBy(AdminModel model) {
        return adminMapper.findBy(model);
    }

    @Override
    public PageInfo<AdminModel> findListBy(AdminCriteria criteria) {
        int pageSize = Optional.ofNullable(criteria.getPageSize()).orElse(10);
        int currPage = Optional.ofNullable(criteria.getCurrPage()).orElse(1);
        return PageHelper.startPage(currPage, pageSize).doSelectPageInfo(() -> adminMapper.findListBy(criteria));
    }

}
