/**
 * @description 创建图形业务
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, Rect, Circle, Polygon } from "@mainapp/lib";

/**
 * 创建流程
 *
 * @param graph
 */
export function addFlow(graph: dia.Graph) {
  Rect.createPrimaryRectangle(graph, { x: 30, y: 30 });
}

/**
 * 创建判定
 *
 * @param graph
 */
export function addDecision(graph: dia.Graph) {
  Polygon.createPrimaryPolygon(graph, { x: 30, y: 30 });
}

/**
 * 创建页面内引用
 *
 * @param graph
 */
export function addOnPageReference(graph: dia.Graph) {
  Circle.createPrimaryCircle(graph, { x: 30, y: 30 });
}
