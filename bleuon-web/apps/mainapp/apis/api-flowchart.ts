/**
 * @description FlowChart API
 * @author 郑人滏 42020306
 * @since 2023/9/28
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 更新当前流程图
 *
 * @param json
 */
export function updateOne(
  data: { json: string; id: string; fileName: string },
  success?: Function,
  error?: Function
) {
  request
    .post<R>("/flowchart/update/one", data)
    .then(({ data }) => {
      success && success(data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 获取流程图存储的 json
 */
export async function queryOne(params: { id: string }) {
  const { data } = await request.get<R>("/flowchart/query/one", { params });
  return data.data;
}
