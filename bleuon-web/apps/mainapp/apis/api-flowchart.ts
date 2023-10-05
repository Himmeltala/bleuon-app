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
 * @param body
 * @param success
 * @param error
 */
export function updateOne(body: FlowchartData, success?: (data: R) => void, error?: Function) {
  request
    .post<R>("/flowchart/update/one", body)
    .then(({ data }) => {
      success && success(data);
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 获取流程图
 *
 * @param params
 * @returns
 */
export async function findOne(params: { id: string }) {
  const { data } = await request.get<R<FlowchartData>>("/flowchart/find/one", { params });
  return data.data;
}

/**
 * 获取流程图
 *
 * @param params
 * @param error
 * @returns
 */
export async function exposeFindOne(params: { id: string }, error?: Function) {
  try {
    const { data } = await request.get<R<FlowchartData>>("/expose/flowchart/find/one", { params });
    return data.data;
  } catch (err) {
    error && error(err);
  }
}

/**
 * 获取用户所有的流程图
 *
 * @param body
 * @returns
 */
export async function findAll(body?: {
  collates?: { isAsc: boolean; col: string }[];
  fileName?: string;
  isPublic?: number;
  isShare?: number;
  isLegal?: number;
}) {
  const { data } = await request.post<R<FlowchartData[]>>("/flowchart/find/all", body);
  return data.data;
}

/**
 * 创建一个流程图
 *
 * @param success
 * @param error
 */
export function createOne(success: (body: FlowchartData) => void, error?: Function) {
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
 *
 * @param data
 * @param success
 * @param error
 */
export function cloneOne(
  data: FlowchartData,
  success?: (data: FlowchartData) => void,
  error?: Function
) {
  request
    .post<R<FlowchartData>>("/flowchart/clone/one", data)
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
 * @param success
 * @param error
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

/**
 * 查询所有的收藏的流程图
 *
 * @param params
 * @returns
 */
export async function findAllCollect(params: { fileName?: string }) {
  const { data } = await request.get<R<any[]>>("/flowchart/find/all/collect", { params });
  return data.data;
}

/**
 * 收藏一个流程图
 *
 * @param body
 * @param success
 * @param error
 */
export async function addOneCollect(
  body: { flowchartId: string },
  success?: Function,
  error?: Function
) {
  request
    .post("/flowchart/add/one/collect", body)
    .then(() => {
      success && success();
    })
    .catch(err => {
      error && error(err);
    });
}

/**
 * 删除一个收藏的流程图
 *
 * @param params
 * @param success
 */
export async function deleteOneCollect(params: { flowchartId: string }, success?: Function) {
  await request.delete<R<void>>("/flowchart/delete/one/collect", { params }).then(() => {
    success && success();
  });
}
