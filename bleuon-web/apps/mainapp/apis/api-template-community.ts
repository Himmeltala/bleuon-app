/**
 * @description 模板社区 API
 * @author 郑人滏 42020306
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

/**
 * 收藏该流程图
 *
 * @param body
 * @param success
 */
export function collectOne(body: TemplateFlowchartData, success?: (res: R) => void) {
  request.post<R>("/community/template/collect/one", body).then(({ data }) => success && success(data));
}


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
 * 获取一个流程图模板
 *
 * @param params
 * @returns
 */
export async function findOne(params: TemplateFlowchartData) {
  const { data } = await request.get<R<TemplateFlowchartData>>("/community/template/find/one", {
    params
  });
  return data.data;
}

/**
 * 导入模板到自己的控制台
 *
 * @param body
 * @param success
 * @param error
 */
export async function cloneOne(body: TemplateFlowchartData, success?: (res: R) => void, error?: Function) {
  request.post<R>("/community/template/clone/one", body).then(({ data }) => {
    success && success(data);
  }).catch(err => {
    error && error(err);
  });
}

/**
 * 更新模板数据
 *
 * @param body
 * @param success
 * @param error
 */
export async function updateOne(body: TemplateFlowchartData, success?: (res: R) => void, error?: Function) {
  request.put("/community/template/update/one", body).then(({ data }) => {
    success && success(data);
  }).catch(err => {
    error && error(err);
  });
}