package com.bleuon.service;

import com.bleuon.constant.AuthorityType;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.dto.ConsumerDTO;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.service.impl.AdminService;
import com.bleuon.service.impl.ConsumerService;
import com.bleuon.utils.http.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntranceService {

    private final AuthorityMapper authorityMapper;
    private final ConsumerService consumerService;
    private final AdminService adminService;

    public R<Object> resetPassword(ConsumerModel model) {
        boolean success = consumerService.upgrade(model);
        if (success) {
            return R.success("密码重置成功！");
        } else {
            return R.failed("密码重置失败！");
        }
    }

    @Transactional
    public R<Object> registerByAccount(ConsumerModel model) {
        try {
            ConsumerModel exists = consumerService.findByUname(model.getUsername());

            if (!Objects.isNull(exists)) {
                return R.error("用户名已被注册！");
            }

            model.setId(UUID.randomUUID().toString());
            boolean status = consumerService.add(model);

            if (status) {
                authorityMapper.setConsumerAuthority(model.getId(), AuthorityType.USER, model.getUsername());
                return R.success("注册成功！");
            } else {
                return R.error("注册失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public R<Object> registerByEmail(ConsumerModel model) {
        try {
            ConsumerDTO exists = consumerService.findByEmail(model.getEmail());

            if (!Objects.isNull(exists)) {
                return R.error("邮箱已被注册！");
            }

            String uuid = UUID.randomUUID().toString();
            model.setId(uuid);
            model.setUsername("用户_" + uuid);
            boolean status = consumerService.add(model);

            if (status) {
                authorityMapper.setConsumerAuthority(model.getId(), AuthorityType.USER, model.getUsername());
                return R.success("注册成功！");
            } else {
                return R.error("注册失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
