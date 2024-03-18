/**
 * @description 用户 API
 * @author zheng
 * @since 2023/9/9
 */

import { http } from "@common/requests/use-axios";

/**
 * 获取验证码
 *
 * @param model
 * @param success
 */
export function askMailCaptcha(
  model: {
    email: string;
  },
  success?: Function
) {
  http.get<R>("/public/entrance/aks/email-captcha", { params: model }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱登录验证码
 *
 * @param model
 * @param success
 */
export function askLoginEmailCaptcha(
  req: ReqConfig<undefined, { email: string }>,
  success?: Function
) {
  http.get<R>("/public/entrance/ask/login-email-captcha", req.config).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取邮箱注册验证码
 *
 * @param model
 * @param success
 */
export function askRegisterEmailCaptcha(
  model: {
    email: string;
  },
  success?: Function
) {
  http.get<R>("/public/entrance/ask/register-email-captcha", { params: model }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 获取密码重置的邮箱验证码
 *
 * @param model
 * @param success
 */
export function askResetEmailCaptcha(
  model: {
    email: string;
  },
  success?: Function
) {
  http.get<R>("/public/entrance/ask/reset-email-captcha", { params: model }).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 校验邮箱验证码
 *
 * @param model
 * @param success
 */
export function verifyEmailCaptcha(
  model: {
    email: string;
    captcha: string;
  },
  success: (res: Token) => void
) {
  http.post<R<Token>>("/public/entrance/verify/email-captcha", model).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 校验邮箱验证码注册
 *
 * @param model
 * @param params
 * @param success
 */
export function verifyRegisterEmailCaptcha(
  model: ConsumerModel,
  params: { email: string; captcha: string },
  success?: Function
) {
  http
    .post<R>("/public/entrance/verify/register-email-captcha", model, { params })
    .then(({ data }) => {
      success && success(data);
    });
}

/**
 * 校验邮箱验证码登录
 *
 * @param model
 * @param success
 */
export function verifyLoginEmailCaptcha(
  model: {
    email: string;
    captcha: string;
  },
  success: (res: Token) => void
) {
  http.post<R<Token>>("/public/entrance/verify/login-email-captcha", model).then(({ data }) => {
    success(data.data);
  });
}

/**
 * 邮箱、用户名或手机号注册
 *
 * @param model
 * @param success
 */
export function accountRegister(model: ConsumerModel, success?: Function) {
  http.post<R>("/public/entrance/account-register", model).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 邮箱、用户名或手机号登录
 *
 * @param model
 * @param success
 */
export function authLogin(model: ConsumerModel, success: (res: Token) => void) {
  http.post<R<Token>>("/public/entrance/account-login", model).then(({ data }) => {
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
  http.post<R>("/auth/logout").then(({ data }) => {
    if (data.code === 200) {
      location.reload();
      localStorage.removeItem(Consts.MAINAPP_TOKEN_KEY);
      success && success(data);
    } else error && error();
  });
}

/**
 * 重置密码
 *
 * @param model 用户实体类
 * @param success
 */
export function resetPassword(model: ConsumerModel, success: Function) {
  http.post<R>("/public/entrance/reset-password", model).then(({ data }) => {
    success(data);
  });
}

/**
 * 查询用户
 *
 * @param id
 * @returns
 */
export async function findBy(params: ConsumerModel) {
  const { data } = await http.get<R<ConsumerModel>>(`/consumer/find/by`, { params });
  return data.data;
}

/**
 * 更新用户资料
 *
 * @param model
 * @param success
 */
export async function upgrade(model: ConsumerModel, success?: Function) {
  http.put<R>(`/consumer/upgrade`, model).then(({ data }) => {
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
  return http.put<R>(`/consumer/upgrade/avatar/${id}`, formData);
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
  const { data } = await http.post<R<DynamicModel[]>>(
    "/consumer/find/all/dynamic/criteria",
    criteria
  );
  return data.data;
}

/**
 * 更新动态
 *
 * @param model id
 * @param success
 */
export function upgradeDynamic(req: ReqConfig<DynamicModel>, success?: Function) {
  http.put<R>("/consumer/upgrade/dynamic", req.model, req.config).then(() => {
    success && success();
  });
}

/**
 * 删除动态
 *
 * @param model
 * @param success
 */
export function deleteDynamic(model: DynamicModel, success?: Function) {
  http.delete<R>("/consumer/delete/dynamic", { params: model }).then(() => {
    success && success();
  });
}

/**
 * 新增动态
 *
 * @param model
 * @param success
 */
export function addDynamic(model: DynamicModel, success?: Function) {
  http.post<R>("/consumer/add/dynamic", model).then(() => {
    success && success();
  });
}

/**
 * 查询公开、分享的流程图
 *
 * @param model
 * @returns
 */
export async function findAllFlowchart(model: {
  collectorId: string;
  isPublic?: 1 | 0;
  isShare?: 1 | 0;
}) {
  const { data } = await http.get<R<FlowchartModel[]>>("/consumer/find/all/flowchart", {
    params: model
  });
  return data.data;
}

/**
 * 查询关注的用户列表
 *
 * @param model
 * @returns
 */
export async function findAllCollectingConsumerByCriteria(model: {
  collectorId: string;
  remark?: string;
  sequences?: { isAsc: boolean; col: string }[];
}) {
  const { data } = await http.get<R<CollectingConsumerModel[]>>(
    "/consumer/find/all/collecting/criteria",
    { params: model }
  );
  return data.data;
}

/**
 * 新增一个关注用户
 *
 * @param model
 * @param success
 */
export function addCollecting(model: CollectingConsumerModel, success?: Function) {
  http.post<R>("/consumer/add/collecting", model).then(() => {
    success && success();
  });
}

/**
 * 删除一个关注用户
 *
 * @param model
 * @param success
 */
export function deleteCollecting(model: CollectingConsumerModel, success?: Function) {
  http.delete<R>("/consumer/delete/collecting", { params: model }).then(() => {
    success && success();
  });
}
