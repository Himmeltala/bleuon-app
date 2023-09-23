/**
 * @description 创建图形业务
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, createPrimaryRectangle } from "@mainapp/lib";

/**
 * 创建 primary 矩形
 *
 * @param graph
 */
export function addPrimaryRectangle(graph: dia.Graph) {
  createPrimaryRectangle(graph, { x: 30, y: 30 });
}
