package com.bleuon.controller.open;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.ConsumerModel;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.dto.ConsumerDTO;
import com.bleuon.entity.dto.TokenDTO;
import com.bleuon.entity.vo.EmailCaptchaVO;
import com.bleuon.service.EmailCaptchaService;
import com.bleuon.service.EntranceService;
import com.bleuon.service.TokenService;
import com.bleuon.service.impl.ConsumerService;
import com.bleuon.utils.http.IpUtil;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @description: 入口接口
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/8/25
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/public/entrance")
@Tag(name = "入口")
public class EntranceController {

    private final ConsumerService consumerService;
    private final TokenService tokenService;
    private final EntranceService entranceService;
    private final EmailCaptchaService captchaService;

    @Operation(summary = "获取邮箱验证码，没有其他逻辑，输入邮箱直接获取验证码")
    @GetMapping("/aks/email-captcha")
    public R<Object> askMailCaptcha(@Validated EmailCaptchaVO vo,
                                    HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(vo.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");
        String captcha = captchaService.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = captchaService.transmit(vo.getEmail(), "BleuOn：验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @Operation(summary = "获取登录邮箱验证码，在此之前判断邮箱是否存在")
    @GetMapping("/ask/login-email-captcha")
    public R<Object> askLoginMailCaptcha(@Validated EmailCaptchaVO vo,
                                         HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(vo.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        ConsumerDTO exists = consumerService.findByEmail(vo.getEmail());
        if (Objects.isNull(exists)) {
            return R.error("邮箱不存在！");
        }

        String captcha = captchaService.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = captchaService.transmit(vo.getEmail(), "BleuOn：登录验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }


    @Operation(summary = "获取注册邮箱验证码，在此之前判断邮箱是否被注册")
    @GetMapping("/ask/register-email-captcha")
    public R<Object> askRegisterMailCaptcha(@Validated EmailCaptchaVO vo,
                                            HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(vo.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        ConsumerDTO exists = consumerService.findByEmail(vo.getEmail());
        if (!Objects.isNull(exists)) {
            return R.error("邮箱被注册！");
        }

        String captcha = captchaService.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = captchaService.transmit(vo.getEmail(), "BleuOn：注册验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @Operation(summary = "获取重置密码邮箱验证码，在此之前判断邮箱是否注册或存在")
    @GetMapping("/ask/reset-email-captcha")
    public R<Object> askResetMailCaptcha(@Validated EmailCaptchaVO vo,
                                         HttpServletRequest http) {
        boolean duplicate = captchaService.memorize(vo.getEmail(), IpUtil.getIp(http));
        if (duplicate) return R.error("60s 内只能发送一次！");

        ConsumerDTO exists = consumerService.findByEmail(vo.getEmail());
        if (Objects.isNull(exists)) {
            return R.error("邮箱没有注册！");
        }

        String captcha = captchaService.getCaptcha(vo.getEmail() + ":captcha");
        boolean status = captchaService.transmit(vo.getEmail(), "BleuOn：密码重置验证码", "您的验证码是：" + captcha + ", 验证码有效期：3 分钟。");
        return status ? R.success("验证码发送成功，请注意查收！") : R.error("验证码发送失败！");
    }

    @Operation(summary = "校验邮箱验证码，需判断是否过期或存在")
    @PostMapping("/verify/email-captcha")
    public R<TokenDTO> verifyMailCode(@RequestBody @Validated EmailCaptchaVO vo) {
        String key = vo.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！");

        if (captchaService.hasCaptchaEqual(key, vo.getCaptcha())) {
            captchaService.removeCaptcha(key);
            return R.success("校验成功！");
        } else return R.error("验证码错误！");
    }


    @Operation(summary = "校验注册验证码")
    @PostMapping("/verify/register-email-captcha")
    public R<Object> verifyRegisterMailCaptcha(@RequestBody @Validated ConsumerModel model,
                                               @Validated EmailCaptchaVO vo) {
        String key = model.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！");

        if (captchaService.hasCaptchaEqual(key, vo.getCaptcha())) {
            captchaService.removeCaptcha(key);
            return entranceService.registerByEmail(model);
        } else return R.error("验证码错误！");
    }

    @Operation(summary = "校验登录验证码")
    @PostMapping("/verify/login-email-captcha")
    public R<TokenDTO> verifyLoginMailCaptcha(@RequestBody @Validated EmailCaptchaVO vo) {
        String key = vo.getEmail() + ":captcha";
        boolean expired = captchaService.hasCaptchaExpire(key);
        if (expired) return R.error("验证码过期或不存在！");

        if (captchaService.hasCaptchaEqual(key, vo.getCaptcha())) {
            captchaService.removeCaptcha(key);

            ConsumerDTO exists = consumerService.findByEmail(vo.getEmail());
            if (Objects.isNull(exists)) {
                return R.error("该用户不存在！");
            } else {
                TokenDTO token = tokenService.grant(new CustomUserDetails(exists.getId(),
                        exists.getUsername(),
                        "******", new ArrayList<>()));
                return R.success("登录成功！", token);
            }
        } else return R.error("验证码错误！");
    }

    @Operation(summary = "用户名称注册")
    @PostMapping("/account-register")
    public R<Object> accountRegister(@RequestBody @Validated ConsumerModel model) {
        return entranceService.registerByAccount(model);
    }

    @Operation(summary = "重置用户密码")
    @PostMapping("/reset-password")
    public R<Object> resetPassword(@RequestBody @Validated ConsumerModel model) {
        return entranceService.resetPassword(model);
    }

}
