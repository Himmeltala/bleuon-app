package com.bleuon.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.AuthorityType;
import com.bleuon.constant.MailType;
import com.bleuon.entity.User;
import com.bleuon.entity.vo.AuthVo;
import com.bleuon.mapper.AuthMapper;
import com.bleuon.mapper.UserMapper;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.NumbersUtil;
import com.bleuon.utils.http.R;
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
     * @param type  验证码类型
     * @param ip    请求 IP
     */
    public R<Void> getMailVerifyCode(String email, String type, String ip) {
        setCacheCode(type + ":" + email);
        setSender(type + ":" + email + ":" + ip);

        if (isMultiGet())
            return R.failed("60s 内只能获取一次验证码！");

        rememberIp(ip);

        if (type.equals("register"))
            return getRegisterVerifyCode(email, type);
        else
            return getNotRegisterVerifyCode(email, type);
    }

    /**
     * 校验邮箱验证码
     *
     * @param user 实体类
     * @param type 验证码类型
     * @param code 验证码
     */
    @Transactional
    public R<AuthVo> verifyMailCode(User user, String type, String code) {
        setCacheCode(type + ":" + user.getEmail());
        String cacheCode = redisTemplate.opsForValue().get(getCacheCode());

        if (cacheCode == null)
            return R.failed("验证码和邮箱不匹配！", null);

        if (!cacheCode.equals(code))
            return R.failed("验证码不存在，或与邮箱不匹配！", null);

        if (type.equals("register"))
            return verifyRegisterMailCode(user);
        else if (type.equals("login"))
            return verifyLoginMailCode(user);
        else
            redisTemplate.delete(getCacheCode());
        return R.success("验证成功，进行下一步！", null);
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

    private R<Void> getNotRegisterVerifyCode(String email, String type) {
        if (isMailExist(email))
            return startWriteMail(email, type);
        else return R.failed("邮箱没有注册！");
    }

    private R<Void> getRegisterVerifyCode(String email, String type) {
        if (!isMailExist(email))
            return startWriteMail(email, type);
        else return R.failed("该邮箱被注册！");
    }

    private R<Void> startWriteMail(String email, String type) {
        String code = generateCode();
        boolean f = publishMailByType(email, type, code);
        if (f) return R.success("验证码发送成功，请注意查收！");
        return R.failed("验证码发送失败，请重试！");
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

    private R<AuthVo> verifyRegisterMailCode(User user) {
        if (findUserByEmail(user.getEmail()) == null) {
            User u = createUser(user);

            boolean f = save(u);
            authMapper.setAuthority(u.getId(), AuthorityType.USER, u.getUsername());

            if (f) {
                redisTemplate.delete(getCacheCode());
                return R.success("注册成功！", null);
            }

            return R.failed("注册失败！", null);
        } else return R.failed("邮箱已经被注册！", null);
    }

    private User createUser(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setUsername("用户_" + uuid);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    private R<AuthVo> verifyLoginMailCode(User u) {
        User user = findUserByEmail(u.getEmail());

        if (user != null) {
            String uuid = UUID.randomUUID().toString();
            Long expire = JwtUtil.getExpire();
            String token = JwtUtil.createJwt(user, uuid, expire);

            redisTemplate.opsForValue().set(uuid, token, expire, TimeUnit.SECONDS);

            AuthVo vo = new AuthVo();

            vo.setToken(token);
            vo.setExpire(JwtUtil.getExpire());

            redisTemplate.delete(getCacheCode());

            return R.success("登录成功！", vo);
        } else {
            return R.failed("邮箱没有注册！", null);
        }
    }

    private User findUserByEmail(String email) {
        return query().eq("email", email).one();
    }

}
