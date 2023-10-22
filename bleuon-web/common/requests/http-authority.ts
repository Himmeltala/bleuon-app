/**
 * @description 权限管理
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
export async function findAllAdmin(model: { pageSize: number; currPage: number }) {
  const { data } = await http.post<R<PageInfo<AdminModel>>>("/authority/find/all/admin", model);
  return data.data;
}
