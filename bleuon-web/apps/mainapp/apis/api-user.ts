/**
 * @description 用户 API
 * @author zheng
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 邮箱、用户名或手机号登录
 */
export function accountLogin(body: UserData, success: (res: TokenR) => void) {
  const headers = {
    "Content-Type": "application/x-www-form-urlencoded"
  };

  request.post<R<TokenR>>("/auth/login", body, { headers }).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 校验邮箱验证码登录
 */
export function loginWithMailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: TokenR) => void
) {
  request.post<R<TokenR>>("/entrance/login/with/mail-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 请求退出登录
 */
export function logout(success: Function, error?: Function) {
  request
    .post<R>("/auth/logout")
    .then(({ data }) => {
      if (data.code === 200) {
        localStorage.removeItem(KeyVals.MAINAPP_TOKEN_KEY);
        success && success(data);
      } else error && error();
    })
    .catch(err => {
      error && error();
    });
}

/**
 * 邮箱、用户名或手机号注册
 */
export function accountRegister(body: UserData, success?: Function, error?: Function) {
  request
    .post<R>("/entrance/account-register", body)
    .then(({ data }) => {
      success && success(data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 校验邮箱验证码注册
 */
export function registerWithMailCaptcha(
  body: UserData,
  params: { email: string; captcha: string },
  success?: Function
) {
  request.post<R>("/entrance/register/with/mail-captcha", body, { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取验证码
 *
 * @param params
 */
export function askMailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/aks-mail-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取密码重置的邮箱验证码
 */
export function askResetMailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/ask/reset-mail-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱登录验证码
 */
export function askLoginMailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/ask/login-mail-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱注册验证码
 */
export function askRegisterMailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/ask/register-mail-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 校验邮箱验证码
 */
export function verifyMailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: TokenR) => void
) {
  request.post<R<TokenR>>("/entrance/verify-mail-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 重置密码
 *
 * @param body 用户实体类
 */
export function resetPassword(body: UserData, success: Function) {
  request.post<R>("/entrance/reset-password", body).then(({ data }) => {
    success(data);
  });
}

/**
 * 通过 token 查询用户信息
 */
export async function fineByToken() {
  const { data } = await request.get<R<UserData>>("/user/find/by/token");
  return data.data;
}

/**
 * 通过 token 更新用户资料
 *
 * @param body
 * @param success
 * @param error
 */
export async function renewalByToken(body: UserData, success?: Function, error?: Function) {
  request
    .post<R>("/user/renewal/by/token", body)
    .then(({ data }) => {
      success && success(data);
    })
    .catch(err => {
      error && error();
    });
}
