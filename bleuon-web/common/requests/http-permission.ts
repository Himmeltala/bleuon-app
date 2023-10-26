/**
 * @description 权限和角色
 * @author zheng
 * @since 2023/10/23
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { http } from "@common/requests/use-axios";

/**
 * 获取单个管理员，包括权限和角色
 *
 * @param params
 * @returns
 */
export async function findAdminWithRoleAndAuthorityList(params: { adminId: string }) {
  const { data } = await http.get<R<AdminModel>>(
    "/permission/find/admin/with/role-and-authority-list",
    {
      params
    }
  );
  return data.data;
}

/**
 * 获取所有管理员，包括权限和角色
 *
 * @param model
 * @returns
 */
export async function findAllAdminWithRoleAndAuthorityList(model: Criteria) {
  const { data } = await http.post<R<PageInfo<AdminModel>>>(
    "/permission/find/all/admin/with/role-and-authority-list",
    model
  );
  return data.data;
}

/**
 * 查询角色分组，包括权限列表
 *
 * @param model
 * @returns
 */
export async function findAllRoleWithAuthorityList(model: Criteria) {
  const { data } = await http.post<R<PageInfo<RoleModel>>>(
    "/permission/find/all/role/with-authority-list",
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
export async function findAllRoleButNoAuthorityList(criteria: Criteria) {
  const { data } = await http.post<R<PageInfo<RoleModel>>>(
    `/permission/find/all/role/but-no-authority-list`,
    criteria
  );
  return data.data;
}

/**
 * 查询角色的权限列表
 *
 * @param criteria
 * @returns
 */
export function findAuthorityListOfRole(
  criteria: Criteria<{ roleId: number }>,
  success: (data: PageInfo<AuthorityModel>) => void
) {
  http
    .post<R<PageInfo<AuthorityModel>>>(`/permission/find/authority-list-of-role`, criteria)
    .then(({ data }) => {
      success(data.data);
    });
}

/**
 * 新增一个角色
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
 * 删除一个角色
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
 * 修改一个角色
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

/**
 * 添加权限列表到权限角色关联表中
 *
 * @param model
 * @param success
 */
export function addAuthorityListToRole(
  model: { roleId: number; authIds: number[] },
  success: Function
) {
  http.post<R>("/permission/add/authority/list/to/role", model).then(() => {
    success();
  });
}

/**
 * 删除角色权限
 *
 * @param params
 * @param success
 */
export function deleteRoleAuthority(params: { roleId: number; authId: number }, success: Function) {
  http.delete<R>("/permission/delete/role/authority", { params }).then(() => {
    success();
  });
}

/**
 * 将角色分配给管理员
 *
 * @param model
 * @param success
 */
export function addRoleToAdmin(
  model: { adminId: string; roleId: number; username: string },
  success?: Function
) {
  http.post<R>("/permission/add/role/to/admin", model).then(() => {
    success && success();
  });
}
