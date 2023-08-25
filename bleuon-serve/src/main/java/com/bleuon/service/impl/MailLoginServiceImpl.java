package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.consts.Codes;
import com.bleuon.consts.MailType;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVoR;
import com.bleuon.mapper.UserMapper;
import com.bleuon.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MailLoginServiceImpl extends ServiceImpl<UserMapper, User> {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private boolean hasMail(String mail) {
        User user = query().eq("email", mail).one();
        return user != null;
    }

    public AuthVoR getMailVerifyCode(String mail, String type, String ip) {
        AuthVoR vo = new AuthVoR();

        Boolean hasKey = redisTemplate.hasKey(type + ":" + mail + ":" + ip);
        if (Boolean.TRUE.equals(hasKey)) {
            vo.setMessage("同一时间内发送过多的邮箱验证码，请稍后再试！");
            vo.setCode(Codes.SEND_MULTI_EMAIL);
            return vo;
        } else {
            redisTemplate.opsForValue().set(type + ":" + mail + ":" + ip, ip, 1, TimeUnit.MINUTES);
        }

        if (hasMail(mail)) {
            String verifyCode = generateVerifyCode();
            redisTemplate.opsForValue().set(type + ":" + mail, verifyCode, 1, TimeUnit.MINUTES);
            boolean isOk = sendMail(mail, type, verifyCode);
            if (isOk) {
                vo.setMessage("验证码发送成功，请注意查收！");
                vo.setCode(Codes.SUCCESS);
            } else {
                vo.setMessage("验证码发送失败，请稍后再试！");
                vo.setCode(Codes.SEND_EMAIL_FAILED);
            }
        } else {
            vo.setMessage("邮箱不存在！");
            vo.setCode(Codes.EMAIL_NONE);
        }

        return vo;
    }

    private String generateVerifyCode() {
        Random random = new Random();
        int coed = random.nextInt(899999) + 100000;
        return Integer.toString(coed);
    }

    private boolean sendMail(String mail, String type, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("himmelbleu@qq.com");
            message.setTo(mail);

            if (Objects.equals(type, MailType.LOGIN)) {
                message.setSubject("BleuOn：" + "登录账号验证码");
            } else if (Objects.equals(type, MailType.RESET)) {
                message.setSubject("BleuOn：" + "密码重置验证码");
            } else if (Objects.equals(type, MailType.REGISTER)) {
                message.setSubject("BleuOn：" + "账号注册验证码");
            } else {
                return false;
            }

            message.setText("您的验证码是：" + code);
            mailSender.send(message);

            return true;
        } catch (MailException e) {
            return false;
        }
    }

    public AuthVoR verifyMailCode(String mail, String type, String code) {
        AuthVoR vo = new AuthVoR();
        String redisCode = redisTemplate.opsForValue().get(type + ":" + mail);
        if (redisCode == null) {
            vo.setMessage("验证码不存在或已过期！");
            vo.setCode(Codes.EMAIL_CODE_ERROR);
        } else {
            if (redisCode.equals(code)) {
                User user = findUserByFiled(mail);
                if (user != null) {
                    String jwtUuid = UUID.randomUUID().toString();
                    Long expire = JwtUtil.getExpire();
                    String token = JwtUtil.createJwt(user, jwtUuid, expire);

                    redisTemplate.opsForValue().set(jwtUuid, token, expire, TimeUnit.SECONDS);

                    vo.setToken(token);
                    vo.setExpire(JwtUtil.getExpire());
                    vo.setMessage("登录成功！");
                    vo.setCode(Codes.SUCCESS);
                } else {
                    vo.setMessage("邮箱不存在！");
                    vo.setCode(Codes.EMAIL_NONE);
                }
            } else {
                vo.setMessage("验证码与发送邮箱不匹配！");
                vo.setCode(Codes.EMAIL_CODE_ERROR);
            }
        }
        return vo;
    }

    public User findUserByFiled(String filed) {
        return query()
                .eq("username", filed)
                .or()
                .eq("email", filed)
                .or()
                .eq("phone", filed)
                .one();
    }

}
