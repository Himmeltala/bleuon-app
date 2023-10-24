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
 * 查询角色分组以及权限列表
 *
 * @param model
 * @returns
 */
export async function findAllRoleWithAuthorityList(model: { pageSize: number; currPage: number }) {
  const { data } = await http.post<R<PageInfo<RoleModel>>>(
    "/permission/find/all/role/with/authority/list",
    model
  );
  return data.data;
}

/**
 * 新增角色
 *
 * @param model
 * @param success
 */
export function addRole(model: RoleModel, success?: Function) {
  http.post<R>("/permission/add/role", model).then(() => {
    success && success();
  });
}
