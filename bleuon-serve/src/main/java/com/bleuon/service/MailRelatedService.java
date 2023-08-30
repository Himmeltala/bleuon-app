package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.HttpCode;
import com.bleuon.constant.MailType;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.entity.vo.Vo;
import com.bleuon.mapper.AuthMapper;
import com.bleuon.mapper.UserMapper;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.NumbersUtil;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 电子邮箱相关服务
 * <p>
 * 1. 获取验证码。注册、登录、重置密码业务。
 * 2. 校验验证码。校验 Redis 中是否存在验证码。
 *
 * @author zheng
 */
@Service
public class MailRelatedService extends ServiceImpl<UserMapper, User> {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private AuthMapper authMapper;

    @Value("${spring.mail.sender}")
    private String senderMailAddress;

    @Getter
    @Setter
    private String cacheCode;

    @Getter
    @Setter
    private String sender;

    /**
     * 获取邮箱验证码
     *
     * @param email 电子邮箱地址
     * @param type 验证码类型
     * @param ip   请求 IP
     */
    public Vo getMailVerifyCode(String email, String type, String ip) {
        setCacheCode(type + ":" + email);
        setSender(type + ":" + email + ":" + ip);

        Vo vo = new Vo();

        if (isMultiGet()) {
            vo.setMessage("60s 内只能获取一次验证码！");
            vo.setCode(HttpCode.ERROR);
            return vo;
        }

        rememberIp(ip);

        if (type.equals("register"))
            getVerifyCodeByRegister(email, type, vo);
        else
            getVerifyCodeByMailNoRegister(email, type, vo);


        return vo;
    }

    /**
     * 校验邮箱验证码
     *
     * @param user 实体类
     * @param type 验证码类型
     * @param code 验证码
     */
    @Transactional
    public AuthVo verifyMailCode(User user, String type, String code) {
        setCacheCode(type + ":" + user.getEmail());
        String cacheCode = redisTemplate.opsForValue().get(getCacheCode());

        AuthVo vo = new AuthVo();

        if (cacheCode == null) {
            vo.setMessage("验证码和邮箱不匹配！");
            vo.setCode(HttpCode.ERROR);
            return vo;
        }

        if (!cacheCode.equals(code)) {
            vo.setMessage("验证码不存在，或与邮箱不匹配！");
            vo.setCode(HttpCode.ERROR);
            return vo;
        }

        if (type.equals("register"))
            verifyRegisterMailCode(user, vo);
        else if (type.equals("login"))
            verifyLoginMailCode(user, vo);
        else
            vo.setMessage("验证成功，进行下一步！");
        vo.setCode(HttpCode.SUCCESS);

        redisTemplate.delete(getCacheCode());

        return vo;
    }

    private boolean isMultiGet() {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getSender()));
    }

    private void rememberIp(String ip) {
        redisTemplate.opsForValue().set(getSender(), ip, 1, TimeUnit.MINUTES);
    }

    private boolean isMailExist(String email) {
        return query().eq("email", email).one() != null;
    }

    private void getVerifyCodeByMailNoRegister(String email, String type, Vo vo) {
        if (isMailExist(email)) {
            startWriteMail(email, type, vo);
        } else {
            vo.setMessage("邮箱未注册！");
            vo.setCode(HttpCode.ERROR);
        }
    }

    private void getVerifyCodeByRegister(String email, String type, Vo vo) {
        if (!isMailExist(email)) {
            startWriteMail(email, type, vo);
        } else {
            vo.setMessage("邮箱被注册！");
            vo.setCode(HttpCode.ERROR);
        }
    }

    private void startWriteMail(String email, String type, Vo vo) {
        String code = generateCode();
        boolean o = publishMailByType(email, type, code);
        vo.setMessage(o ? "验证码发送成功，请注意查收！" : "验证码发送失败，请重试！");
        vo.setCode(o ? HttpCode.SUCCESS : HttpCode.ERROR);
    }

    private String generateCode() {
        String code = String.valueOf(NumbersUtil.random(100000, 999999));
        redisTemplate.opsForValue().set(getCacheCode(), code, 3, TimeUnit.MINUTES);
        return code;
    }

    private boolean publishMailByType(String email, String type, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderMailAddress);
            message.setTo(email);

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

    public void verifyRegisterMailCode(User user, AuthVo vo) {
        if (findUserByEmail(user.getEmail()) == null) {
            startTransaction(vo, user);
        } else {
            vo.setMessage("邮箱已被注册！");
            vo.setCode(HttpCode.ERROR);
        }
    }

    private void verifyLoginMailCode(User u, AuthVo vo) {
        User user = findUserByEmail(u.getEmail());

        if (user != null) {
            String uuid = UUID.randomUUID().toString();
            Long expire = JwtUtil.getExpire();
            String token = JwtUtil.createJwt(user, uuid, expire);

            redisTemplate.opsForValue().set(uuid, token, expire, TimeUnit.SECONDS);

            vo.setToken(token);
            vo.setExpire(JwtUtil.getExpire());
            vo.setMessage("登录成功！");
            vo.setCode(HttpCode.SUCCESS);

            redisTemplate.delete(getCacheCode());
        } else {
            vo.setMessage("邮箱未注册！");
            vo.setCode(HttpCode.ERROR);
        }
    }

    private User findUserByEmail(String email) {
        return query().eq("email", email).one();
    }

    public void startTransaction(Vo vo, User u) {
        User user = createUser(u);

        boolean o = save(user);
        authMapper.setAuthority(user.getId(), 3L, user.getUsername());

        if (o) vo.setData(user);

        vo.setCode(o ? HttpCode.SUCCESS : HttpCode.ERROR);
        vo.setMessage(o ? "注册成功！" : "注册失败！");
    }

    private User createUser(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setUsername("用户_" + uuid);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

}
