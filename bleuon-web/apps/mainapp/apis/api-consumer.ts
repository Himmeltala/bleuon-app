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
  request.get<R>("/public/entrance/aks/email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱登录验证码
 *
 * @param params
 * @param success
 */
export function askLoginEmailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/public/entrance/ask/login-email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱注册验证码
 *
 * @param params
 * @param success
 */
export function askRegisterEmailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/public/entrance/ask/register-email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取密码重置的邮箱验证码
 *
 * @param params
 * @param success
 */
export function askResetEmailCaptcha(
  params: {
    email: string;
  },
  success?: Function
) {
  request.get<R>("/public/entrance/ask/reset-email-captcha", { params }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 校验邮箱验证码
 *
 * @param body
 * @param success
 */
export function verifyEmailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: Token) => void
) {
  request.post<R<Token>>("/public/entrance/verify/email-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 校验邮箱验证码注册
 *
 * @param body
 * @param params
 * @param success
 */
export function verifyRegisterEmailCaptcha(
  body: ConsumerModel,
  params: { email: string; captcha: string },
  success?: Function
) {
  request
    .post<R>("/public/entrance/verify/register-email-captcha", body, { params })
    .then(({ data }) => {
      success && success(data);
    });
}

/**
 * 校验邮箱验证码登录
 *
 * @param body
 * @param success
 */
export function verifyLoginEmailCaptcha(
  body: {
    email: string;
    captcha: string;
  },
  success: (res: Token) => void
) {
  request.post<R<Token>>("/public/entrance/verify/login-email-captcha", body).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 邮箱、用户名或手机号注册
 *
 * @param body
 * @param success
 */
export function accountRegister(body: ConsumerModel, success?: Function) {
  request.post<R>("/public/entrance/account-register", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 邮箱、用户名或手机号登录
 *
 * @param body
 * @param success
 */
export function authLogin(body: ConsumerModel, success: (res: Token) => void) {
  const headers = {
    "Content-Type": "application/x-www-form-urlencoded"
  };

  request.post<R<Token>>("/auth/login", body, { headers }).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 请求退出登录
 *
 * @param success
 * @param error
 */
export function authLogout(success?: Function, error?: Function) {
  request.post<R>("/auth/logout").then(({ data }) => {
    if (data.code === 200) {
      location.reload();
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
export function resetPassword(body: ConsumerModel, success: Function) {
  request.post<R>("/public/entrance/reset-password", body).then(({ data }) => {
    success(data);
  });
}

/**
 * 查询用户信息
 *
 * @param id
 * @returns
 */
export async function findById(id: string) {
  const { data } = await request.get<R<ConsumerModel>>(`/consumer/find/${id}`);
  return data.data;
}

/**
 * 更新用户资料
 *
 * @param body
 * @param success
 */
export async function upgrade(body: ConsumerModel, success?: Function) {
  request.put<R>(`/consumer/upgrade`, body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 更新用户头像
 *
 * @param formData
 * @param id
 * @returns
 */
export function upgradeAvatar(formData: FormData, id: string) {
  return request.put<R>(`/consumer/upgrade/avatar/${id}`, formData);
}

/**
 * 查询动态列表
 *
 * @param consumerId
 * @returns
 */
export async function findAllDynamicByCriteria(criteria: {
  consumerId: string;
  sequences?: { isAsc: boolean; col: string }[];
}) {
  const { data } = await request.post<R<DynamicModel[]>>(
    "/consumer/find/all/dynamic/criteria",
    criteria
  );
  return data.data;
}

/**
 * 更新动态
 *
 * @param data id
 * @param success
 */
export function upgradeDynamic(data: DynamicModel, success?: Function) {
  request
    .put<R>("/consumer/upgrade/dynamic", data, {
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
export function deleteDynamic(params: DynamicModel, success?: Function) {
  request.delete<R>("/consumer/delete/dynamic", { params }).then(() => {
    success && success();
  });
}

/**
 * 新增动态
 *
 * @param body
 * @param success
 */
export function addDynamic(body: DynamicModel, success?: Function) {
  request.post<R>("/consumer/add/dynamic", body).then(() => {
    success && success();
  });
}

/**
 * 查询公开、分享的流程图
 *
 * @param params
 * @returns
 */
export async function findAllFlowchart(params: {
  collectingCid: string;
  isPublic?: 1 | 0;
  isShare?: 1 | 0;
}) {
  const { data } = await request.get<R<FlowchartModel[]>>("/consumer/find/all/flowchart", {
    params
  });
  return data.data;
}

/**
 * 查询关注的用户列表
 *
 * @param params
 * @returns
 */
export async function findAllCollectingConsumerByCriteria(params: {
  collectingCid: string;
  remark?: string;
  sequences?: { isAsc: boolean; col: string }[];
}) {
  const { data } = await request.get<R<CollectingConsumerModel[]>>(
    "/consumer/find/all/collecting/criteria",
    { params }
  );
  return data.data;
}
