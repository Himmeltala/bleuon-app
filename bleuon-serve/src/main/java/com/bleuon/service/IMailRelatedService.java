package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.entity.dto.AuthDto;
import com.bleuon.utils.http.R;

public interface IMailRelatedService {

    R<Void> getMailVerifyCode(String email, String type, String ip);

    R<AuthDto> verifyMailCode(User user, String type, String code);

}
