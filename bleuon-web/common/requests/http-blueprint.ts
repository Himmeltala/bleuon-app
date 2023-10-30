/**
 * @description 模板 API
 * @author zheng
 * @since 2023/10/5
 */

import { http } from "@common/requests/use-axios";

/**
 * 获取所有流程图模板
 *
 * @param model
 * @returns
 */
export async function findAll(model?: BlueprintFlowchartModel) {
  const { data } = await http.get<R<BlueprintFlowchartModel[]>>("/blueprint/find/all", {
    params: model
  });
  return data.data;
}

/**
 * 获取流程图模板
 *
 * @param model
 * @returns
 */
export async function findById(model: BlueprintFlowchartModel) {
  const { data } = await http.get<R<BlueprintFlowchartModel>>("/blueprint/find/by/id", {
    params: model
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
  http.post<R>(`/blueprint/replicate/${consumerId}`, model).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 更新模板
 *
 * @param model
 * @param config
 * @param success
 */
export async function upgrade(req: ReqConfig<BlueprintFlowchartModel>, success?: (res: R) => void) {
  http.put("/blueprint/upgrade", req.model, req.config).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 收藏该流程图
 *
 * @param model
 * @param success
 */
export function addCollecting(
  model: BlueprintFlowchartModel,
  consumerId: string,
  success?: (res: R) => void
) {
  http
    .post<R>(`/blueprint/add/collecting/${consumerId}`, model)
    .then(({ data }) => success && success(data));
}
