/**
 * @description 模板社区 API
 * @author 郑人滏 42020306
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */

import request from "./use-axios";

export async function findAll(params?: TemplateFlowchartData) {
  const { data } = await request.get<R<TemplateFlowchartData[]>>("/community/template/find/all", {
    params
  });
  return data.data;
}
