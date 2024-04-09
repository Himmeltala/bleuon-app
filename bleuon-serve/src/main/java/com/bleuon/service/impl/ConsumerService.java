package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.Constants;
import com.bleuon.entity.ConsumerModel;
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
    public ConsumerModel findBy(ConsumerModel model) {
        return consumerMapper.findBy(model);
    }

    @Override
    public ConsumerModel findByUsernameOrPhoneOrEmailAndPwd(ConsumerModel model) {
        ConsumerModel result = consumerMapper.findByUsernameOrPhoneOrEmail(model);

        if (Objects.isNull(result)) return null;

        if (!StringUtils.hasText(result.getPassword())) return null;

        boolean matches = passwordEncoder.matches(model.getPassword(), result.getPassword());

        if (!matches) return null;

        return result;
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
            String imgUrl = fileService.upload(Constants.RESOURCE_ROOT + "/static/images/avatar", model.getId(), file);

            if (!StringUtils.hasText(imgUrl)) {
                return "";
            }

            model.setAvatar(imgUrl);
            boolean upgraded = upgrade(model);

            if (!upgraded) {
                return "";
            }

            return imgUrl;
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

    @Transactional
    @Override
    public boolean add(ConsumerModel model) {
        try {
            model.setPassword(passwordEncoder.encode(model.getPassword()));
            return save(model);
        } catch (Exception e) {
            throw new JdbcErrorException(e);
        }
    }

}
