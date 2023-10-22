package com.bleuon.entity.vo;

import com.bleuon.constant.ValidPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/8
 */
@Schema(description = "邮箱验证码模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailCaptchaVO implements Serializable {

    @Email(message = "不是合法的邮箱地址！")
    private String email;

    @Pattern(regexp = ValidPattern.DIGIT_6, message = "验证码必须是 6 位正整数！")
    private String captcha;

}
