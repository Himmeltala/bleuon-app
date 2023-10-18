/**
 * @description Cell API
 * @author zheng
 * @since 2023/9/27
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "@common/apis/use-axios";

/**
 * 通过条件查询图形列表
 *
 * @param model
 * @returns
 */
export async function findAllByCriteria(model: { type: "basic" | "flowchart" }) {
  const { data } = await request.get<R>("/cell/find/all/by/criteria", { params: model });
  return data.data;
}
