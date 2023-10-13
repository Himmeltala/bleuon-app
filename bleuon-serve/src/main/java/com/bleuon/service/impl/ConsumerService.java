package com.bleuon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Consumer;
import com.bleuon.entity.dto.ConsumerDto;
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
public class ConsumerService extends ServiceImpl<ConsumerMapper, Consumer> implements IConsumerService {

    private final ConsumerMapper mapper;
    private final FileService fileService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public ConsumerDto findById(String id) {
        Consumer exists = query().eq("id", id).one();
        if (Objects.isNull(exists)) return null;

        ConsumerDto dto = new ConsumerDto();
        BeanUtil.copyProperties(exists, dto);
        return dto;
    }

    public ConsumerDto findByEmail(String email) {
        Consumer exists = query().eq("email", email).one();
        if (Objects.isNull(exists)) return null;

        ConsumerDto dto = new ConsumerDto();
        BeanUtil.copyProperties(exists, dto);
        return dto;
    }

    @Transactional
    @Override
    public boolean upgrade(Consumer consumer) {
        try {
            if (consumer.getPassword() != null) {
                consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
            }

            consumer.setModifyDate(new Timestamp(new Date().getTime()));
            Integer status = mapper.upgrade(consumer);
            return status > 0;
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

    @Transactional
    @Override
    public String upgradeAvatar(Consumer consumer, MultipartFile file) {
        try {
            String imgUrl = fileService.upload("/static/images/avatar", consumer.getId(), file);

            if (StringUtils.hasText(imgUrl) && upgrade(consumer)) {
                return imgUrl;
            }

            return "";
        } catch (Exception e) {
            throw new JdbcErrorException(e.getCause());
        }
    }

}
