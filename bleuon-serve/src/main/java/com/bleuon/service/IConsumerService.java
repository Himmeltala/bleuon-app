package com.bleuon.service;

import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.dto.ConsumerDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/6
 */
public interface IConsumerService {

    ConsumerDTO findById(String id);

    ConsumerDTO findByEmail(String email);

    ConsumerDTO findByAnyUniqueFiled(ConsumerModel model);

    boolean upgrade(ConsumerModel model);

    String upgradeAvatar(ConsumerModel model, MultipartFile file);
}
