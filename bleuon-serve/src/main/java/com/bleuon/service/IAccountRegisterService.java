package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.utils.http.R;

public interface IAccountRegisterService {

    R<Object> registerByAccount(User body);

    R<Object> registerByEmail(User body);
}
