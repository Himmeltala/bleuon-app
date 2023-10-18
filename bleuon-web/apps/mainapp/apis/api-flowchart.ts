/**
 * @description FlowChart API
 * @author zheng
 * @since 2023/9/28
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "@common/apis/use-axios";

/**
 * 更新流程图
 *
 * @param model
 * @param success
 */
export function upgrade(
  model: FlowchartModel,
  config?: { nomessage: boolean },
  success?: (data: R) => void
) {
  request.put<R>("/flowchart/upgrade", model, config).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 通过 id 获取流程图
 *
 * @param model
 * @returns
 */
export async function findById(model: { id: string }) {
  const { data } = await request.get<R<FlowchartModel>>("/flowchart/find/by/id", { params: model });
  return data.data;
}

/**
 * 获取流程图
 *
 * @param model
 * @param error
 * @returns
 */
export async function findIsShare(model: { id: string }, error?: Function) {
  try {
    const { data } = await request.get<R<FlowchartModel>>("/public/flowchart/find/share", {
      params: model
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
  collectingCid: string;
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
export function add(model: { consumerId: string }, success: (res: R<FlowchartModel>) => void) {
  request.post<R<FlowchartModel>>("/flowchart/add", model).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 复制一个流程图
 *
 * @param model
 * @param success
 */
export function replicate(model: FlowchartModel, success?: (res: R) => void) {
  request.post<R<FlowchartModel>>("/flowchart/replicate", model).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 删除一个流程图
 *
 * @param model
 * @param success
 */
export function deleteById(model: { id?: string }, success: Function) {
  request
    .delete<R>("/flowchart/delete/by/id", {
      params: model
    })
    .then(() => {
      success && success();
    });
}

/**
 * 查询所有的收藏的流程图
 *
 * @param model
 * @returns
 */
export async function findAllCollectByCriteria(model: {
  fileName?: string;
  collectingCid: string;
}) {
  const { data } = await request.get<R<FlowchartModel[]>>(
    "/flowchart/find/all/collect/by/criteria",
    {
      params: model
    }
  );
  return data.data;
}

/**
 * 收藏流程图
 *
 * @param model
 * @param success
 */
export async function addCollecting(
  model: { flowchartId: string; collectingCid: string },
  success?: Function
) {
  request.post("/flowchart/add/collecting", model).then(() => {
    success && success();
  });
}

/**
 * 删除收藏的流程图
 *
 * @param model
 * @param success
 */
export async function deleteCollecting(
  model: { flowchartId: string; collectingCid: string },
  success?: Function
) {
  await request.delete<R>("/flowchart/delete/collecting", { params: model }).then(() => {
    success && success();
  });
}

/**
 * 发布和公开流程图到模板社区
 *
 * @param model
 * @param success
 */
export function release(model: BlueprintFlowchartModel, success?: Function) {
  request.post<R>("/flowchart/release", model).then(() => {
    success && success();
  });
}

/**
 * 取消发布和公开一个流程图
 *
 * @param model
 * @param success
 */
export function cancelRelease(model: { flowchartId: string }, success?: Function) {
  request.delete<R>("/flowchart/cancel/release", { params: model }).then(() => {
    success && success();
  });
}
