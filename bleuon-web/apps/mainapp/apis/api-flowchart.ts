/**
 * @description FlowChart API
 * @author 郑人滏 42020306
 * @since 2023/9/28
 * @link https://github.com/himmelbleu/bleuon-app
 */
import qs from "qs";
import request from "./use-axios";

/**
 * 更新当前流程图
 *
 * @param json
 */
export function updateOne(data: FlowchartData, success?: (data: R) => void, error?: Function) {
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
 * 获取流程图
 */
export async function queryOne(params: { id: string }) {
  const { data } = await request.get<R<FlowchartData>>("/flowchart/query/one", { params });
  return data.data;
}

/**
 * 获取流程图
 */
export async function exposeQueryOne(params: { id: string }, error?: Function) {
  try {
    const { data } = await request.get<R<FlowchartData>>("/expose/flowchart/query/one", { params });
    return data.data;
  } catch (err) {
    error && error(err);
  }
}

/**
 * 获取用户所有的流程图
 */
export async function queryAll(params: { type: "asc" | "desc"; cols: string[] }) {
  const { data } = await request.get<R<FlowchartData[]>>("/flowchart/query/all", {
    params,
    paramsSerializer: params => {
      return qs.stringify(params, { arrayFormat: "repeat" });
    }
  });
  return data.data;
}

/**
 * 创建一个流程图
 */
export function createOne(success: (data: FlowchartData) => void, error?: Function) {
  request
    .post<R<FlowchartData>>("/flowchart/create/one")
    .then(({ data }) => {
      success && success(data.data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 复制一个流程图
 */
export function copyOne(
  data: FlowchartData,
  success: (data: FlowchartData) => void,
  error?: Function
) {
  request
    .post<R<FlowchartData>>("/flowchart/copy/one", data)
    .then(({ data }) => {
      success && success(data.data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 删除一个流程图
 *
 * @param params
 */
export function deleteOne(params: { id?: string }, success: Function, error?: Function) {
  request
    .delete<R<void>>("/flowchart/delete/one", {
      params
    })
    .then(() => {
      success && success();
    })
    .catch(err => {
      error && error(err);
    });
}
