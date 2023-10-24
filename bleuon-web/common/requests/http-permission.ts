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
export async function findAllAdminsWithAuthorityList(model: Criteria) {
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
export async function findAllRoleWithAuthorityList(model: Criteria) {
  const { data } = await http.post<R<PageInfo<RoleModel>>>(
    "/permission/find/all/role/with/authority/list",
    model
  );
  return data.data;
}

/**
 * 查询角色分组，但是没有权限列表
 *
 * @param criteria
 * @returns
 */
export async function findAllRole(criteria: Criteria) {
  const { data } = await http.post<R<PageInfo<RoleModel>>>(`/permission/find/all/role`, criteria);
  return data.data;
}

/**
 * 查询角色分组，但是没有权限列表
 *
 * @param criteria
 * @returns
 */
export function findRoleAuthorityList(
  criteria: Criteria<{ roleId: number }>,
  success: (data: PageInfo<AuthorityModel>) => void
) {
  http
    .post<R<PageInfo<AuthorityModel>>>(`/permission/find/role/authority/list`, criteria)
    .then(({ data }) => {
      success(data.data);
    });
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

/**
 * 删除角色
 *
 * @param params
 * @param success
 */
export function deleteRole(params: RoleModel, success?: Function) {
  http.delete<R>("/permission/delete/role", { params }).then(() => {
    success && success();
  });
}

/**
 * 更新角色
 *
 * @param params
 * @param success
 */
export function upgradeRole(model: RoleModel, success?: Function) {
  http.put<R>("/permission/upgrade/role", model).then(() => {
    success && success();
  });
}

/**
 * 查询所有权限
 *
 * @param success
 */
export function findAllAuthorityList(
  criteria: Criteria<{ roleId: number }>,
  success: (data: AuthorityModel[]) => void
) {
  http
    .get<R<AuthorityModel[]>>("/permission/find/all/authority/list", { params: criteria })
    .then(({ data }) => {
      success && success(data.data);
    });
}

export function addAuthorityListToRole(
  model: { roleId: number; authIds: number[] },
  success: Function
) {
  http.post<R>("/permission/add/authority/list/to/role", model).then(() => {
    success();
  });
}

export function deleteRoleAuthority(params: { roleId: number; authId: number }, success: Function) {
  http.delete<R>("/permission/delete/role/authority", { params }).then(() => {
    success();
  });
}
