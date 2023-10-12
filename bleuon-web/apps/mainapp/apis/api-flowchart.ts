/**
 * @description FlowChart API
 * @author zheng
 * @since 2023/9/28
 * @link https://github.com/himmelbleu/bleuon-app
 */
import request from "./use-axios";

/**
 * 更新流程图
 *
 * @param body
 * @param success
 */
export function upgrade(body: FlowchartModel, success?: (data: R) => void) {
  request.put<R>("/flowchart/upgrade", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 通过 id 获取流程图
 *
 * @param params
 * @returns
 */
export async function findById(params: { id: string }) {
  const { data } = await request.get<R<FlowchartModel>>("/flowchart/find/by/id", { params });
  return data.data;
}

/**
 * 获取流程图
 *
 * @param params
 * @param error
 * @returns
 */
export async function findIsShare(params: { id: string }, error?: Function) {
  try {
    const { data } = await request.get<R<FlowchartModel>>("/public/flowchart/find/share", {
      params
    });
    return data.data;
  } catch (err) {
    error && error(err);
  }
}

/**
 * 通过条件查询获取流程图列表
 *
 * @param criteria
 * @returns
 */
export async function findAllByCriteria(criteria: {
  sequences?: { isAsc: boolean; col: string }[];
  fileName?: string;
  isPublic?: number;
  isShare?: number;
  isLegal?: number;
}) {
  const { data } = await request.post<R<FlowchartModel[]>>(
    "/flowchart/find/all/by/criteria",
    criteria
  );
  return data.data;
}

/**
 * 创建流程图
 *
 * @param success
 */
export function add(success: (res: R<FlowchartModel>) => void) {
  request.post<R<FlowchartModel>>("/flowchart/add").then(({ data }) => {
    success && success(data);
  });
}

/**
 * 复制一个流程图
 *
 * @param body
 * @param success
 */
export function replicate(body: FlowchartModel, success?: (res: R) => void) {
  request.post<R<FlowchartModel>>("/flowchart/replicate", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 删除一个流程图
 *
 * @param params
 * @param success
 */
export function deleteById(params: { id?: string }, success: Function) {
  request
    .delete<R<void>>("/flowchart/delete/by/id", {
      params
    })
    .then(() => {
      success && success();
    });
}

/**
 * 查询所有的收藏的流程图
 *
 * @param params
 * @returns
 */
export async function findAllCollectByCriteria(params: { fileName?: string }) {
  const { data } = await request.get<R<FlowchartModel[]>>(
    "/flowchart/find/all/collect/by/criteria",
    {
      params
    }
  );
  return data.data;
}

/**
 * 收藏一个流程图
 *
 * @param body
 * @param success
 */
export async function addCollect(body: { flowchartId: string }, success?: Function) {
  request.post("/flowchart/add/collect", body).then(() => {
    success && success();
  });
}

/**
 * 删除一个收藏的流程图
 *
 * @param params
 * @param success
 */
export async function deleteCollect(params: { flowchartId: string }, success?: Function) {
  await request.delete<R<void>>("/flowchart/delete/collect", { params }).then(() => {
    success && success();
  });
}

/**
 * 发布和公开流程图到模板社区
 *
 * @param body
 * @param success
 */
export function release(body: BlueprintFlowchartModel, success?: Function) {
  request.post<R<void>>("/flowchart/release", body).then(() => {
    success && success();
  });
}

/**
 * 取消发布和公开一个流程图
 *
 * @param params
 * @param success
 */
export function cancelRelease(params: { flowchartId: string }, success?: Function) {
  request.delete<R<void>>("/flowchart/cancel/release", { params }).then(() => {
    success && success();
  });
}
