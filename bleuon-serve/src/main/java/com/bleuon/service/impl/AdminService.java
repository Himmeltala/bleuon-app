package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.criterias.AdminCriteria;
import com.bleuon.mapper.AdminMapper;
import com.bleuon.service.IAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
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
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AdminModel findByUsernameOrPhoneOrEmailAndPwd(AdminModel model) {
        AdminModel result = adminMapper.findByUsernameOrPhoneOrEmail(model);

        if (Objects.isNull(result)) return null;

        if (!StringUtils.hasText(result.getPassword())) return null;

        boolean matches = passwordEncoder.matches(model.getPassword(), result.getPassword());

        if (!matches) return null;

        return result;
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
