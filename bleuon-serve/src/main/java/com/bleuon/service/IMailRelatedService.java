package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.entity.dto.Token;
import com.bleuon.utils.http.R;

public interface IMailRelatedService {

    R getMailVerifyCode(String email, String type, String ip);

    R<Token> verifyMailCode(User user, String type, String code);

}
