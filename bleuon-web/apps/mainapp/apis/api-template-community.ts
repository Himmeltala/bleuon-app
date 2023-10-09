/**
 * @description 模板社区 API
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
export async function findAll(params?: TemplateFlowchartData) {
  const { data } = await request.get<R<TemplateFlowchartData[]>>("/community/template/find/all", {
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
export async function findById(params: TemplateFlowchartData) {
  const { data } = await request.get<R<TemplateFlowchartData>>("/community/template/find/by/id", {
    params
  });
  return data.data;
}

/**
 * 导入模板
 *
 * @param body
 * @param success
 */
export async function replicate(body: TemplateFlowchartData, success?: (res: R) => void) {
  request.post<R>("/community/template/replicate", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 更新模板
 *
 * @param body
 * @param success
 */
export async function renewal(body: TemplateFlowchartData, success?: (res: R) => void) {
  request.put("/community/template/renewal", body).then(({ data }) => {
    success && success(data);
  });
}

/**
 * 收藏该流程图
 *
 * @param body
 * @param success
 */
export function addCollect(body: TemplateFlowchartData, success?: (res: R) => void) {
  request
    .post<R>("/community/template/add/collect", body)
    .then(({ data }) => success && success(data));
}
