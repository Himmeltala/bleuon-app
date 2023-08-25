package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.consts.Codes;
import com.bleuon.consts.MailType;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVoR;
import com.bleuon.entity.vo.VoR;
import com.bleuon.mapper.UserMapper;
import com.bleuon.utils.JwtUtil;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MailRelatedService extends ServiceImpl<UserMapper, User> {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.sender}")
    private String senderMailAddress;

    @Getter
    @Setter
    private String cacheCodeKey;

    @Getter
    @Setter
    private String singleGetKey;

    /**
     * 获取邮箱验证码
     *
     * @param mail 电子邮箱地址
     * @param type 验证码类型
     * @param ip   请求 IP
     */
    public VoR getMailVerifyCode(String mail, String type, String ip) {
        VoR vo = new VoR();

        setCacheCodeKey(type + ":" + mail);
        setSingleGetKey(type + ":" + mail + ":" + ip);
        Boolean isMultiGet = redisTemplate.hasKey(getSingleGetKey());

        if (Boolean.TRUE.equals(isMultiGet)) {
            vo.setMessage("60s 内只能获取一次验证码！");
            vo.setCode(Codes.SEND_MULTI_EMAIL);
            return vo;
        } else {
            redisTemplate.opsForValue().set(getSingleGetKey(), ip, 1, TimeUnit.MINUTES);
        }

        if (type.equals("register")) {
            this.getRegisterVerifyCode(mail, type, vo);
        } else {
            this.getLoginVerifyCode(mail, type, vo);
        }

        return vo;
    }

    /**
     * 校验邮箱验证码
     *
     * @param mail 邮箱地址
     * @param type 验证码类型
     * @param code 验证码
     */
    public AuthVoR verifyMailCode(String mail, String type, String code) {
        AuthVoR vo = new AuthVoR();

        setCacheCodeKey(type + ":" + mail);
        String cacheCode = redisTemplate.opsForValue().get(getCacheCodeKey());

        if (cacheCode == null) {
            vo.setMessage("验证码不存或校验的邮箱不匹配！");
            vo.setCode(Codes.EMAIL_CODE_ERROR);
        } else {
            if (cacheCode.equals(code)) {

                if (type.equals("register")) {
                    this.verifyRegisterMailCode(mail, vo);
                } else {
                    this.verifyLoginMailCode(mail, vo);
                }

                redisTemplate.delete(getCacheCodeKey());
            } else {
                vo.setMessage("验证码与校验的邮箱不匹配！");
                vo.setCode(Codes.EMAIL_CODE_ERROR);
            }
        }
        return vo;
    }

    private boolean isMailExist(String mail) {
        User user = query().eq("email", mail).one();
        return user != null;
    }

    private void getLoginVerifyCode(String mail, String type, VoR vo) {
        if (this.isMailExist(mail)) {
            this.sendVerifyCode(mail, type, vo);
        } else {
            vo.setMessage("邮箱未注册！");
            vo.setCode(Codes.EMAIL_NONE);
        }
    }

    private void getRegisterVerifyCode(String mail, String type, VoR vo) {
        if (!this.isMailExist(mail)) {
            this.sendVerifyCode(mail, type, vo);
        } else {
            vo.setMessage("邮箱被注册！");
            vo.setCode(Codes.EMAIL_EXISTS);
        }
    }

    private void sendVerifyCode(String mail, String type, VoR vo) {
        String verifyCode = this.generateVerifyCode();
        redisTemplate.opsForValue().set(getCacheCodeKey(), verifyCode, 3, TimeUnit.MINUTES);
        boolean isOk = this.generateVerifyCodeMail(mail, type, verifyCode);
        if (isOk) {
            vo.setMessage("验证码发送成功，请注意查收！");
            vo.setCode(Codes.SUCCESS);
        } else {
            vo.setMessage("验证码发送失败，请重试！");
            vo.setCode(Codes.SEND_EMAIL_FAILED);
        }
    }

    private String generateVerifyCode() {
        Random random = new Random();
        int coed = random.nextInt(899999) + 100000;
        return Integer.toString(coed);
    }

    private boolean generateVerifyCodeMail(String mail, String type, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderMailAddress);
            message.setTo(mail);

            if (Objects.equals(type, MailType.LOGIN)) {
                message.setSubject("BleuOn：" + "账号登录验证码");
            } else if (Objects.equals(type, MailType.RESET)) {
                message.setSubject("BleuOn：" + "密码重置验证码");
            } else if (Objects.equals(type, MailType.REGISTER)) {
                message.setSubject("BleuOn：" + "账号注册验证码");
            } else {
                return false;
            }

            message.setText("您的验证码是：" + code + ", 验证码有效期：3 分钟。");
            mailSender.send(message);

            return true;
        } catch (MailException e) {
            return false;
        }
    }


    private void verifyRegisterMailCode(String mail, AuthVoR vo) {
        User isExistUser = this.findUserByEmail(mail);

        if (isExistUser == null) {
            saveUserToDb(vo, mail);
        } else {
            vo.setMessage("邮箱已被注册！");
            vo.setCode(Codes.EMAIL_EXISTS);
        }
    }

    private void verifyLoginMailCode(String mail, AuthVoR vo) {
        User isExistUser = this.findUserByEmail(mail);

        if (isExistUser != null) {
            String uuid = UUID.randomUUID().toString();
            Long expire = JwtUtil.getExpire();
            String token = JwtUtil.createJwt(isExistUser, uuid, expire);

            redisTemplate.opsForValue().set(uuid, token, expire, TimeUnit.SECONDS);

            vo.setToken(token);
            vo.setExpire(JwtUtil.getExpire());
            vo.setMessage("登录成功！");
            vo.setCode(Codes.SUCCESS);

            redisTemplate.delete(getCacheCodeKey());
        } else {
            vo.setMessage("邮箱未注册！");
            vo.setCode(Codes.EMAIL_NONE);
        }
    }

    private User findUserByEmail(String mail) {
        return this.query()
                .eq("email", mail)
                .one();
    }


    private void saveUserToDb(VoR vo, String mail) {
        User u = new User();
        String uuid = UUID.randomUUID().toString();
        u.setId(UUID.randomUUID().toString());
        u.setUsername("用户_" + uuid);
        u.setEmail(mail);
        u.setPassword(passwordEncoder.encode(uuid.substring(0, 8)));
        try {
            boolean isOk = this.save(u);
            if (isOk) {
                vo.setData(u);
                vo.setMessage("注册成功！");
                vo.setCode(Codes.SUCCESS);
            } else {
                vo.setMessage("注册失败！");
                vo.setCode(Codes.ERROR);
            }
        } catch (Exception e) {
            vo.setMessage("注册失败！");
            vo.setCode(Codes.ERROR);
        }
    }

}
