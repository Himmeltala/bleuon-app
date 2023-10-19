package com.bleuon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.dto.ConsumerDTO;
import com.bleuon.exception.JdbcErrorException;
import com.bleuon.mapper.ConsumerMapper;
import com.bleuon.service.FileService;
import com.bleuon.service.IConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @description:
 * @package: com.bleuon.service.impl
 * @author: zheng
 * @date: 2023/10/6
 */
@Service
@RequiredArgsConstructor
public class ConsumerService extends ServiceImpl<ConsumerMapper, ConsumerModel> implements IConsumerService {

    private final FileService fileService;
    private final ConsumerMapper consumerMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public ConsumerDTO findById(String id) {
        ConsumerModel exists = query().eq("id", id).one();
        if (Objects.isNull(exists)) return null;

        ConsumerDTO dto = new ConsumerDTO();
        BeanUtil.copyProperties(exists, dto);
        return dto;
    }

    public ConsumerDTO findByEmail(String email) {
        ConsumerModel exists = query().eq("email", email).one();
        if (Objects.isNull(exists)) return null;

        ConsumerDTO dto = new ConsumerDTO();
        BeanUtil.copyProperties(exists, dto);
        return dto;
    }

    @Transactional
    @Override
    public boolean upgrade(ConsumerModel model) {
        try {
            if (!Objects.isNull(model.getPassword())) {
                model.setPassword(passwordEncoder.encode(model.getPassword()));
            }

            model.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = consumerMapper.upgrade(model);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public String upgradeAvatar(ConsumerModel model, MultipartFile file) {
        try {
            String imgUrl = fileService.upload("/static/images/avatar", model.getId(), file);

            if (!StringUtils.hasText(imgUrl)) {
                return "";
            }

            model.setAvatar(imgUrl);
            boolean upgraded = upgrade(model);

            if (upgraded) {
                return imgUrl;
            }

            return "";
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
