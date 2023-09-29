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
export function updateOne(data: FlowchartData, success?: Function, error?: Function) {
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
  const { data } = await request.get<R<FlowchartData>>("/flowchart/query/one", { params });
  return data.data;
}

/**
 * 获取用户所有的流程图
 *
 * @param params userId
 */
export async function queryAll() {
  const { data } = await request.get<R<FlowchartData[]>>("/flowchart/query/all");
  return data.data;
}

/**
 * 创建一个流程图
 *
 * @returns 返回创建的流程图数据
 */
export async function createOne(success: (data: FlowchartData) => void, error?: Function) {
  request
    .post<R<FlowchartData>>("/flowchart/create/one")
    .then(({ data }) => {
      success && success(data.data);
    })
    .catch(err => {
      error && error(err);
    });
}
