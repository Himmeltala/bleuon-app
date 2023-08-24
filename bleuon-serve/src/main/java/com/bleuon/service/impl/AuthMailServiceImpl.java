package com.bleuon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.consts.Codes;
import com.bleuon.consts.MailType;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVoResponse;
import com.bleuon.mapper.AuthUsernamePasswordMapper;
import com.bleuon.mapper.UserBaseMapper;
import com.bleuon.service.AuthMailService;
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
public class AuthMailServiceImpl extends ServiceImpl<UserBaseMapper, User>
        implements AuthMailService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private AuthUsernamePasswordMapper authUsernamePasswordMapper;

    private boolean isMailExist(String mail) {
        User user = query().eq("email", mail).one();
        return user != null;
    }

    public AuthVoResponse getMailVerifyCode(String mail, String type, String ip) {
        AuthVoResponse vo = new AuthVoResponse();

        Boolean hasKey = redisTemplate.hasKey(type + ":" + mail + ":" + ip);
        if (Boolean.TRUE.equals(hasKey)) {
            vo.setMessage("同一时间内发送过多的邮箱验证码，请稍后再试！");
            vo.setCode(Codes.SEND_MULTI_EMAIL);
            return vo;
        } else {
            redisTemplate.opsForValue().set(type + ":" + mail + ":" + ip, ip, 1, TimeUnit.MINUTES);
        }

        if (isMailExist(mail)) {
            Integer code = generateCode();
            redisTemplate.opsForValue().set(type + ":" + mail, code.toString(), 1, TimeUnit.MINUTES);
            boolean isOk = sendMail(mail, type, code);
            if (isOk) {
                vo.setMessage("验证码发送成功");
                vo.setCode(Codes.SUCCESS);
            } else {
                vo.setMessage("验证码发送失败，请稍后再试！");
                vo.setCode(Codes.SEND_EMAIL_FAILED);
            }
        } else {
            vo.setMessage("邮箱不存在");
            vo.setCode(Codes.EMAIL_NONE);
        }

        return vo;
    }

    private Integer generateCode() {
        Random random = new Random();
        return random.nextInt(899999) + 100000;
    }

    private boolean sendMail(String mail, String type, Integer code) {
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

    public AuthVoResponse verifyMailCode(String mail, String type, String code) {
        AuthVoResponse vo = new AuthVoResponse();
        String redisCode = redisTemplate.opsForValue().get(type + ":" + mail);
        if (redisCode == null) {
            vo.setMessage("验证码已过期！");
            vo.setCode(Codes.EMAIL_CODE_ERROR);
        } else {
            if (redisCode.equals(code)) {
                User user = findUserByUsernameOrEmail(mail);
                String jwtUuid = UUID.randomUUID().toString();
                Long expire = JwtUtil.getExpire();
                String token = JwtUtil.createJwt(user, jwtUuid, expire);

                redisTemplate.opsForValue().set(jwtUuid, token, expire, TimeUnit.SECONDS);

                vo.setToken(token);
                vo.setExpire(JwtUtil.getExpire());
                vo.setMessage("登录成功！");
                vo.setCode(Codes.SUCCESS);
            } else {
                vo.setMessage("验证码不匹配！");
                vo.setCode(Codes.EMAIL_CODE_ERROR);
            }
        }
        return vo;
    }

    public User findUserByUsernameOrEmail(String text) {
        return query()
                .eq("username", text)
                .or()
                .eq("email", text)
                .or()
                .eq("phone", text)
                .one();
    }

}
