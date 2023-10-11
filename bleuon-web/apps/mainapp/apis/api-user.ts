/**
 * @description 用户 API
 * @author zheng
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 获取验证码
 *
 * @param params
 * @param success
 */
export function askMailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/aks/email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱登录验证码
 */
export function askLoginEmailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/ask/login-email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱注册验证码
 */
export function askRegisterEmailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/ask/register-email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取密码重置的邮箱验证码
 */
export function askResetEmailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/entrance/ask/reset-email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 校验邮箱验证码
 */
export function verifyEmailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: TokenR) => void
) {
  request.post<R<TokenR>>("/entrance/verify/email-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 校验邮箱验证码注册
 */
export function verifyRegisterEmailCaptcha(
  body: UserData,
  params: { email: string; captcha: string },
  success?: Function
) {
  request.post<R>("/entrance/verify/register-email-captcha", body, { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 校验邮箱验证码登录
 */
export function verifyLoginEmailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: TokenR) => void
) {
  request.post<R<TokenR>>("/entrance/verify/login-email-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 邮箱、用户名或手机号注册
 */
export function accountRegister(body: UserData, success?: Function) {
  request.post<R>("/entrance/account-register", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 邮箱、用户名或手机号登录
 */
export function authLogin(body: UserData, success: (res: TokenR) => void) {
  const headers = {
    "Content-Type": "application/x-www-form-urlencoded"
  };

  request.post<R<TokenR>>("/auth/login", body, { headers }).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 请求退出登录
 */
export function authLogout(success: Function, error?: Function) {
  request.post<R>("/auth/logout").then(({ data }) => {
    if (data.code === 200) {
      localStorage.removeItem(KeyVals.MAINAPP_TOKEN_KEY);
      success && success(data);
    } else error && error();
  });
}

/**
 * 重置密码
 *
 * @param body 用户实体类
 * @param success
 */
export function resetPassword(body: UserData, success: Function) {
  request.post<R>("/entrance/reset-password", body).then(({ data }) => {
    success(data);
  });
}

/**
 * 查询用户信息
 */
export async function findById(id?: string) {
  const { data } = await request.get<R<UserData>>("/user/find/by/id", { params: { id } });
  return data.data;
}

/**
 * 更新用户资料
 *
 * @param body
 * @param success
 */
export async function upgrade(body: UserData, success?: Function) {
  request.post<R>("/user/upgrade", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 更新用户头像
 *
 * @param formData
 * @returns
 */
export function upgradeAvatar(formData: FormData) {
  return request.post("/user/upgrade/avatar", formData);
}

/**
 * 上传 ckeditor 图片
 *
 * @param file
 * @returns
 */
export function uploadCkEditorImage(file: any) {
  return request.post("/file/upload/ckeditor/image", file);
}

/**
 * 查询动态列表
 *
 * @param uid
 */
export async function findAllDynamic(uid: string) {
  const { data } = await request.get<R<DynamicData[]>>("/user/find/all/dynamic", {
    params: { uid }
  });
  return data.data;
}

/**
 * 更新动态
 *
 * @param data id
 * @param success
 */
export function upgradeDynamic(data: DynamicData, success?: Function) {
  request
    .post<R>("/user/upgrade/dynamic", data, {
      nomessage: true
    })
    .then(() => {
      success && success();
    });
}

/**
 * 删除动态
 *
 * @param params id
 * @param success
 */
export function deleteDynamic(params: DynamicData, success?: Function) {
  request.delete<R>("/user/delete/dynamic", { params }).then(() => {
    success && success();
  });
}
