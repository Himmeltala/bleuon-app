package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.AdminModel;
import com.bleuon.mapper.AdminMapper;
import com.bleuon.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
