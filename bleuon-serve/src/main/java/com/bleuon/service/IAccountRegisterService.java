package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.utils.http.R;

public interface IAccountRegisterService {

    R register(User user);

}
