package com.bleuon.service;

import com.bleuon.entity.ConsumerModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/6
 */
public interface IConsumerService {

    ConsumerModel findBy(ConsumerModel model);

    ConsumerModel findByUsernameOrPhoneOrEmailAndPwd(ConsumerModel model);

    boolean upgrade(ConsumerModel model);

    String upgradeAvatar(ConsumerModel model, MultipartFile file);

    boolean add(ConsumerModel model);

}
