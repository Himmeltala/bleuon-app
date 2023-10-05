/**
 * @description 模板社区 API
 * @author 郑人滏 42020306
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
