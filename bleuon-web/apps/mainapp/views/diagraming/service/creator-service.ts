/**
 * @description 创建图形业务
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, Rect, Circle } from "@mainapp/lib";

/**
 * 创建 primary 矩形
 *
 * @param graph
 */
export function addPrimaryRectangle(graph: dia.Graph) {
  Rect.createPrimaryRectangle(graph, { x: 30, y: 30 });
}

/**
 * 创建 primary 圆形
 *
 * @param graph
 */
export function addPrimaryCircle(graph: dia.Graph) {
  Circle.createPrimaryCircle(graph, { x: 30, y: 30 });
}
