/**
 * @description 管理员 API
 * @author zheng
 * @since 2023/10/22
 */

import { http } from "@common/requests/use-axios";

/**
 * 邮箱、用户名或手机号登录
 *
 * @param model
 * @param success
 */
export function authLogin(model: AdminModel, success: (res: Token) => void) {
  http.post<R<Token>>("/public/entrance/admin-login", model).then(({ data }) => {
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
      localStorage.removeItem(Consts.SUBAPP_TOKEN_KEY);
      success && success(data);
    } else error && error();
  });
}

/**
 * 查询管理员信息
 *
 * @param params
 * @returns
 */
export async function findBy(params: Criteria<AdminModel>) {
  const { data } = await http.get<R<AdminModel>>(`/admin/find/by`, { params });
  return data.data;
}

/**
 * 查询管理员列表
 *
 * @param model
 * @returns
 */
export async function findListBy(model: Criteria<AdminModel>) {
  const { data } = await http.post<R<PageInfo<AdminModel>>>(`/admin/find/list/by`, model);
  return data.data;
}
