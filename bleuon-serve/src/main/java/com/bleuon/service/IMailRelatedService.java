package com.bleuon.service;

import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.utils.http.R;

public interface IMailRelatedService {

    R<Void> getMailVerifyCode(String email, String type, String ip);

    R<AuthVo> verifyMailCode(User user, String type, String code);

}
