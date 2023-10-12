package com.bleuon.service;

import com.bleuon.entity.Consumer;
import com.bleuon.entity.dto.ConsumerDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/6
 */
public interface IConsumerService {

    ConsumerDto findById(String id);

    ConsumerDto findByEmail(String email);

    boolean upgrade(Consumer consumer);

    String upgradeAvatar(Consumer consumer, MultipartFile file);
}
