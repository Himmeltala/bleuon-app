/**
 * @description Cell API
 * @author 郑人滏 42020306
 * @since 2023/9/27
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 获取所有的 cells
 *
 * @param type 图形类型
 */
export async function queryAllCells(type: "basic" | "flowchart") {
  const { data } = await request.get<R>("/cell/official/query/all", { params: { type } });
  return data.data;
}
