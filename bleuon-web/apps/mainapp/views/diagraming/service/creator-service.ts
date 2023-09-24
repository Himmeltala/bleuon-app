/**
 * @description 创建图形业务
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, Rect, Circle, Polygon, Path } from "@mainapp/lib";

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
 * 创建开始/结束
 *
 * @param graph
 */
export function addStartOrEnd(graph: dia.Graph) {
  Rect.createPrimaryRectangle(graph, {
    x: 30,
    y: 30,
    attrs: {
      body: {
        rx: "calc(h / 2)",
        ry: "calc(h / 2)"
      }
    }
  });
}

/**
 * 创建文档
 *
 * @param graph
 */
export function addDocument(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    refD: "M 0 0 L 20 0 L 20 9 C 16 7 15 7 11 8 C 5 10 4 10 0 9 Z"
  });
}

/**
 * 创建数据
 *
 * @param graph
 */
export function addData(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    refD: "M 0 0 L 20 0 L 17 11 L -3 11 Z"
  });
}

/**
 * 插入子流程
 *
 * @param graph
 */
export function addChildFlow(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    refD: "M 0 0 V 10 H 14 V 0 Z M 16 0 L 16 0 V 10 H 14 V 0 Z M -2 0 V 10 H 0 V 0 Z"
  });
}

/**
 * 插入外部数据
 *
 * @param graph
 */
export function addExtraData(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    refD: "M 0 0 H 11 C 9 2 9 5 11 7 H 0 C -2 5 -2 2 0 0 Z"
  });
}

/**
 * 插入内部存储
 *
 * @param graph
 */
export function addInnerStore(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    refD: "M 0 0 H 2 V 2 H 0 Z M 2 0 H 13 V 2 H 2 Z M 0 2 V 8 H 2 V 2 Z M 13 8 V 2 H 2 V 8 Z"
  });
}

/**
 * 插入队列数据
 *
 * @param graph
 */
export function addQueueData(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 80,
    height: 80,
    refD: "M 0 0 A 1 1 0 0 0 0 8 A 1 1 0 0 0 0 0 Z M 0 8 L 4 8 Z"
  });
}

/**
 * 插入横着的数据库
 *
 * @param graph
 */
export function addDatabase1(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    height: 70,
    refD: "M 0 0 H 11 C 10 1 10 5 11 6 H 0 C -1 5 -1 1 0 0 Z M 11 0 C 12 1 12 5 11 6 C 10 5 10 1 11 0 Z"
  });
}

/**
 * 插入竖着的数据库
 *
 * @param graph
 */
export function addDatabase2(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    height: 100,
    width: 70,
    refD: "M 0 0 L 0 10 C 1 11 7 11 8 10 L 8 0 C 7 1 1 1 0 0 M 0 0 C 1 -1 7 -1 8 0 C 7 1 1 1 0 0 Z"
  });
}

/**
 * 创建人工输入
 *
 * @param graph
 */
export function addManualInput(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 0 L 13 -2 V 6 H 0 Z"
  });
}

/**
 * 创建卡片
 *
 * @param graph
 */
export function addCard(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 3 V 8 H 11 V 0 H 5 L 0 3 Z"
  });
}

/**
 * 创建条带
 *
 * @param graph
 */
export function addCassette(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 0 V 5 C 4 5 8 7 12 6 V 1 C 8 2 4 0 0 0 Z"
  });
}

/**
 * 创建展示
 *
 * @param graph
 */
export function addDisplay(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 4 L 2 0 H 9 C 9 3 9 5 9 8 H 2 L 0 4 Z"
  });
}

export function addManualOperation(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 0 H 9 L 7 5 H 2 L 0 0 Z"
  });
}

export function addPrepare(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 0 L 7 0 L 9 3 L 7 6 L 0 6 L -2 3 Z"
  });
}

export function addParallelMode(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 0 H 11 L 11 6 H 0 L 0 0 M 0 -1 L 11 -1 Z M 0 7 L 11 7 Z"
  });
}

export function addCycleLimit(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 3 0 H 7 L 9 2 V 6 H 1 V 2 Z"
  });
}

export function addOr(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    height: 100,
    refD: "M 0 0 A 1 1 0 0 0 0 6 A 1 1 0 0 0 0 0 Z M -3 3 L 3 3 Z M 0 0 L 0 6 Z"
  });
}

export function addCalcSum(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    height: 100,
    refD: "M 0 0 A 1 1 0 0 0 0 6 A 1 1 0 0 0 0 0 Z M -2.5 1.4 L 2.5 4.6 Z M 2.5 1.4 L -2.5 4.6"
  });
}

export function addOffPageReference(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    refD: "M 0 0 H 6 V 3 L 3 5 L 0 3 Z"
  });
}

export function addSorting(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 100,
    height: 100,
    refD: "M 4 0 L 0 4 L 4 8 L 8 4 Z M 0 4 L 8 4 Z"
  });
}

export function addMerge(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 80,
    height: 80,
    refD: "M 0 0 H 6 L 3 5 Z"
  });
}

export function addRemark1(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 10,
    height: 120,
    refD: "M 1 -4 Q 0 -4 0 -3 V 2 C 0 3 -1 3 -1 3 C 0 3 0 4 0 4 V 9 Q 0 10 1 10"
  });
}

export function addRemark2(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 10,
    height: 120,
    refD: "M 0 -4 Q 1 -4 1 -3 V 2 C 1 3 2 3 2 3 C 1 3 1 4 1 4 V 9 Q 1 10 0 10"
  });
}

export function addQuote1(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 10,
    height: 120,
    refD: "M 1 -4 L 0 -4 V 3 V 10 L 1 10"
  });
}

export function addQuote2(graph: dia.Graph) {
  Path.createPrimaryPath(graph, {
    x: 30,
    y: 30,
    width: 10,
    height: 120,
    refD: "M 1 -4 L 2 -4 V 3 V 10 L 1 10"
  });
}

/**
 * 创建页面内引用
 *
 * @param graph
 */
export function addOnPageReference(graph: dia.Graph) {
  Circle.createPrimaryCircle(graph, { x: 30, y: 30 });
}
