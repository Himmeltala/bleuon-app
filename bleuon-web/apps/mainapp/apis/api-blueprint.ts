/**
 * @description 模板 API
 * @author zheng
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 获取所有流程图模板
 *
 * @param params
 * @returns
 */
export async function findAll(params?: BlueprintFlowchartModel) {
  const { data } = await request.get<R<BlueprintFlowchartModel[]>>("/blueprint/find/all", {
    params
  });
  return data.data;
}

/**
 * 获取流程图模板
 *
 * @param params
 * @returns
 */
export async function findById(params: BlueprintFlowchartModel) {
  const { data } = await request.get<R<BlueprintFlowchartModel>>("/blueprint/find/by/id", {
    params
  });
  return data.data;
}

/**
 * 导入模板
 *
 * @param model
 * @param success
 */
export async function replicate(
  model: BlueprintFlowchartModel,
  consumerId: string,
  success?: (res: R) => void
) {
  request.post<R>(`/blueprint/replicate/${consumerId}`, model).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 更新模板
 *
 * @param body
 * @param config
 * @param success
 */
export async function upgrade(
  body: BlueprintFlowchartModel,
  config?: { nomessage: boolean },
  success?: (res: R) => void
) {
  request.put("/blueprint/upgrade", body, config).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 收藏该流程图
 *
 * @param body
 * @param success
 */
export function addCollect(body: BlueprintFlowchartModel, success?: (res: R) => void) {
  request.post<R>("/blueprint/add/collecting", body).then(({ data }) => success && success(data));
}
