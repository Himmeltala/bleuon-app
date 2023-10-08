/**
 * @description 用户 API
 * @author zheng
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 邮箱、用户名或手机号登录
 *
 * @param entity 用户实体类
 */
export function accountLogin(entity: UserData, success: (res: TokenR) => void) {
  const body = {
    username: entity.username,
    password: entity.password
  };

  const headers = {
    "Content-Type": "application/x-www-form-urlencoded"
  };

  request.post<R<TokenR>>("/auth/login", body, { headers }).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 邮箱、用户名或手机号注册
 *
 * @param entity 用户实体类
 */
export function accountRegister(entity: UserData, success?: Function, error?: Function) {
  const body = {
    username: entity.username,
    password: entity.password
  };

  request
    .post<R>("/auth/account-register", body)
    .then(({ data }) => {
      success && success(data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 邮箱注册
 *
 * @param body
 * @param success
 */
export function emailRegister(body: UserData, success?: Function) {
  request.post<R>("/auth/email-register", body).then(({ data }) => {
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
  success?: Function,
  error?: Function
) {
  request
    .get<R>("/auth/aks-mail-captcha", { params })
    .then(({ data }) => {
      success && success(data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 校验邮箱验证码
 *
 * @param body
 */
export function verifyMailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: TokenR) => void
) {
  request.post<R<TokenR>>("/auth/verify-mail-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 重置密码
 *
 * @param entity 用户实体类
 */
export function resetPassword(entity: UserData, success: Function, error?: Function) {
  const body = {
    email: entity.email,
    password: entity.password
  };

  request
    .post<R>("/auth/reset-password", body)
    .then(({ data }) => {
      success(data);
    })
    .catch(err => {
      error && error();
    });
}

/**
 * 请求退出登录
 *
 * @param success 退出登录成功回调函数
 * @param error 退出登录失败回调函数
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
