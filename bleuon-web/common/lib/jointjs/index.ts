/**
 * @description jointjs 初始化
 * @author zheng
 * @since 2023/9/9
 */

import { dia, shapes } from "jointjs";
import { defineNamespace } from "./namespace";

/**
 * 创建 jointjs 对象
 *
 * @param config 配置 jointjs
 * @returns paper, graph
 */
export function createJointjs(config: {
  el: string;
  bgColor: string;
  height: string;
  width: string;
  gridSize: number;
  drawGrid: boolean | dia.Paper.GridOptions | dia.Paper.GridOptions[];
  defaultRouter: any;
  defaultConnector: any;
}) {
  const namespace = defineNamespace();
  const graph = new dia.Graph({}, { cellNamespace: namespace });
  const paper = new dia.Paper({
    el: document.getElementById(config.el),
    model: graph,
    height: config.height,
    width: config.width,
    gridSize: config.gridSize,
    drawGrid: config.drawGrid,
    background: {
      color: config.bgColor
    },
    snapLabels: true,
    snapLinks: { radius: 5 },
    interactive: {
      linkMove: false,
      labelMove: true,
      arrowheadMove: false,
      vertexMove: false,
      vertexAdd: false,
      vertexRemove: false,
      useLinkTools: false
    },
    cellViewNamespace: namespace,
    // @ts-ignore
    defaultLink: () => new shapes.bleuon.Link(),
    defaultConnectionPoint: { name: "boundary" },
    defaultRouter: config.defaultRouter,
    defaultConnector: config.defaultConnector
  });

  return {
    paper,
    graph
  };
}
