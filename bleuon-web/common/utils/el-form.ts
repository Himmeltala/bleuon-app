/**
 * @description 校验器工具
 * @author zheng
 * @since 2023/10/1
 */

import type { FormInstance } from "element-plus";

/**
 * 提交表单之前校验整个表单是否合法
 *
 * @param formEl 表单 Ref 对象
 * @param success 成功回调
 * @param error 失败回调
 */
export async function validate(
  formEl: FormInstance | undefined,
  success?: Function,
  error?: Function
) {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      success && success(valid, fields);
    } else {
      error && error(valid, fields);
      return false;
    }
  });
}

/**
 * 重置表单
 *
 * @param formEl
 * @returns
 */
export function reset(formEl: FormInstance | undefined) {
  if (!formEl) return;
  formEl.resetFields();
}

/**
 * 邮箱验证器
 *
 * @param isCorrect 邮箱地址是否正确的标识变量
 */
export function emailValidator(isCorrect: Ref<boolean>) {
  return (rule: any, value: any, callback: any) => {
    const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!regex.test(value)) {
      isCorrect.value = false;
      callback(new Error("请输入正确的邮箱格式"));
    } else if (value === "") {
      isCorrect.value = false;
      callback(new Error("请输入邮箱地址"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}

/**
 * 验证码验证器
 *
 * @param isCorrect 验证码是否正确的标志变量
 */
export function verifyCaptchaValidator(isCorrect: Ref<boolean>) {
  return (rule: any, value: any, callback: any) => {
    if (value.length < 6) {
      isCorrect.value = false;
      callback(new Error("请输入 6 位数的验证码"));
    } else if (value === "") {
      isCorrect.value = false;
      callback(new Error("请输入验证码"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}

/**
 * 获取验证码
 *
 * @param interval 定时器 ID
 * @param count 倒计时变量，一般是 60s
 * @param disabled 控制发送验证码按钮是否显示的布尔变量
 */
export function askVerifyCaptcha(
  interval: number,
  count: Ref<number>,
  disabled: Ref<boolean>,
  startInterval: (callback: Function) => void
) {
  startInterval(() => {
    interval = setInterval(() => {
      count.value--;
      if (count.value < 0) {
        clearInterval(interval);
        disabled.value = false;
        count.value = 60;
      }
    }, 1000);
  });

  disabled.value = true;
}

/**
 * 用户名验证器
 *
 * @param isCorrect 用户名是否正确的标识变量
 */
export function usernameValidator(isCorrect: Ref<boolean>) {
  return (rule: any, value: any, callback: any) => {
    const regex = /^(?![_-])[\u4e00-\u9fa5a-zA-Z][\u4e00-\u9fa5a-zA-Z0-9_-]{4,16}$/;

    if (!regex.test(value)) {
      isCorrect.value = false;
      callback(new Error("字母、中文、-、_，不能以数字开头、-、_开头，长度在4~16"));
    } else if (value === "") {
      isCorrect.value = false;
      callback(new Error("请输入用户名"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}

/**
 * 密码验证器
 *
 * @param isCorrect 密码是否正确的标识变量
 */
export function passwordValidator(isCorrect: Ref<boolean>) {
  return (rule: any, value: any, callback: any) => {
    const regex = /^[a-zA-Z0-9.]{8,16}$/;

    if (!regex.test(value)) {
      isCorrect.value = false;
      callback(new Error("英文、.、数字，长度在8~16"));
    } else if (value === "") {
      isCorrect.value = false;
      callback(new Error("请输入密码"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}

/**
 * 二次密码验证器
 *
 * @param isCorrect 密码是否正确的标识变量
 */
export function rePasswdValidator(
  isCorrect: Ref<boolean>,
  formData: {
    password: string;
    rePasswd: string;
  }
) {
  return (rule: any, value: any, callback: any) => {
    const regex = /^[a-zA-Z0-9.]{8,16}$/;

    if (!regex.test(value)) {
      isCorrect.value = false;
      callback(new Error("英文、.、数字，长度在8~16"));
    } else if (value === "") {
      isCorrect.value = false;
      callback(new Error("请输入密码"));
    } else if (formData.rePasswd !== formData.password) {
      isCorrect.value = false;
      callback(new Error("两次密码输入不一致"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}

/**
 * 手机号、电子邮箱、用户名格式校验器
 *
 * @param isCorrect 是否正确的标志变量
 */
export function accountValidator(isCorrect: Ref<boolean>) {
  return (rule: any, value: any, callback: any) => {
    const regex =
      /^(?:[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}|(?![_-])[\u4e00-\u9fa5a-zA-Z][\u4e00-\u9fa5a-zA-Z0-9_-]{4,16}|(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8})$/;
    if (!regex.test(value)) {
      isCorrect.value = false;
      callback(new Error("错误的手机号、邮箱、用户名的格式！"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}

/**
 * 文件名称格式校验器
 *
 * @param isCorrect 是否正确的标志变量
 */
export function filenameValidator(isCorrect: Ref<boolean>) {
  return (rule: any, value: any, callback: any) => {
    const regex =
      /^(?:[\u4e00-\u9fa5a-zA-Z][\u4e00-\u9fa5a-zA-Z\d\s\S]{1,28}|[\u4e00-\u9fa5a-zA-Z])$/;
    if (!regex.test(value)) {
      isCorrect.value = false;
      callback(new Error("错误的文件名称！"));
    } else {
      isCorrect.value = true;
      callback();
    }
  };
}
