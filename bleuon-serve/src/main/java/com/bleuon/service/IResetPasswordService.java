package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.utils.http.R;

public interface IResetPasswordService {

    R resetPassword(User user);

}
