package com.bleuon.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @package: com.bleuon.utils
 * @author: zheng
 * @date: 2023/10/24
 */
@Component
@RequiredArgsConstructor
public class EmailUtil {


    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.username}")
    private String address;
    @Value("${spring.mail.timeout}")
    private Integer timeout;
    @Value("${spring.mail.expire}")
    private Integer expire;

    @Getter
    @Setter
    private String principal;

    /**
     * 发送邮箱验证码
     *
     * @param email   邮箱地址
     * @param subject 主题
     * @param text    文本内容
     * @return 发送成功返回 true，反之 false
     */
    public boolean transmit(String email, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(address);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            return false;
        }
    }

    public String getCaptcha(String redisKey) {
        String captcha = String.valueOf(NumbersUtil.random(100000, 999999));
        redisTemplate.opsForValue().set(redisKey, captcha, expire, TimeUnit.MINUTES);
        return captcha;
    }

    /**
     * 记住发送邮件验证码的人
     *
     * @param redisKey   存储到 redis 的 key
     * @param redisValue 存储到 redis 的 value
     * @return 如果记住的这个人已经发送过了就返回 true，否则返回 false
     */
    public boolean memorize(String redisKey, String redisValue) {
        boolean duplicate = Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
        if (duplicate) {
            return true;
        } else {
            redisTemplate.opsForValue().set(redisKey, redisValue, timeout, TimeUnit.MINUTES);
            return false;
        }
    }

    /**
     * 验证码是否过期
     *
     * @param redisKey 存储到 redis 的 key
     * @return 然后 redis 已经没有了这个验证码就返回 false，否则返回 true
     */
    public boolean hasCaptchaExpire(String redisKey) {
        Boolean hasKey = redisTemplate.hasKey(redisKey);
        if (Boolean.TRUE.equals(hasKey)) {
            Long expired = redisTemplate.getExpire(redisKey);
            return Objects.isNull(expired);
        } else {
            return true;
        }
    }

    /**
     * 对比验证码和 redis 验证码是否一致
     *
     * @param redisKey 存储到 redis 的 key
     * @param captcha  存储到 redis 的 captcha
     * @return 如果一致返回 true
     */
    public boolean hasCaptchaEqual(String redisKey, String captcha) {
        String value = redisTemplate.opsForValue().get(redisKey);
        if (value != null) {
            return value.equals(captcha);
        } else {
            return false;
        }
    }

    /**
     * 移除验证码
     *
     * @param redisKey 存储到 redis 的 key
     */
    public boolean removeCaptcha(String redisKey) {
        Boolean delete = redisTemplate.delete(redisKey);
        return Boolean.TRUE.equals(delete);
    }

}
