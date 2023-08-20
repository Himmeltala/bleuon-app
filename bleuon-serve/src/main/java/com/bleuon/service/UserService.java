package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bleuon.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author zheng
 */
public interface UserService extends IService<User>, UserDetailsService {

}
