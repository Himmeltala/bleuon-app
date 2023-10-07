/**
 * @description jointjs 初始化
 * @author zheng
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes } from "jointjs";
import { defineNamespace } from "./namespace";

/**
 * 初始化 jointjs
 *
 * @param config 配置 jointjs
 * @returns paper, graph
 */
export function initJointJs(config: {
  el: string;
  bgColor: string;
  height: string;
  width: string;
  gridSize: number;
  drawGrid: boolean | dia.Paper.GridOptions | dia.Paper.GridOptions[];
  isPreventLinkFromInputPorts?: boolean;
  isPreventLinkFromOutputToInputWithinOneElement?: boolean;
  isPreventLinkToOutputPorts?: boolean;
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
    validateConnection: function (cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
      if (config.isPreventLinkFromInputPorts || false) {
        if (magnetS && magnetS.getAttribute("port-group") === "in") return false;
      }

      if (config.isPreventLinkFromOutputToInputWithinOneElement || false) {
        if (cellViewS === cellViewT) return false;
      }

      if (config.isPreventLinkToOutputPorts || false) {
        return magnetT && magnetT.getAttribute("port-group") === "in";
      }

      return true;
    }
  });

  return {
    paper,
    graph
  };
}
