/**
 * @description 权限和角色
 * @author zheng
 * @since 2023/10/23
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { http } from "@common/requests/use-axios";

/**
 * 获取管理员权限连同获取管理员的模型数据
 *
 * @param model
 * @returns
 */
export async function findAllAdminsWithAuthorityList(model: {
  pageSize: number;
  currPage: number;
}) {
  const { data } = await http.post<R<PageInfo<AdminModel>>>(
    "/permission/find/all/admins/with/authority/list",
    model
  );
  return data.data;
}

/**
 * 获取管理员权限连同获取管理员的模型数据
 *
 * @param model
 * @returns
 */
export async function findAll(model: { pageSize: number; currPage: number }) {
  const { data } = await http.post<R<PageInfo<RoleModel>>>("/permission/find/role", model);
  return data.data;
}
