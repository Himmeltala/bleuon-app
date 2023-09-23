/**
 * @description jointjs 初始化
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { PrimaryLink } from "./shapes/link";
import { dia, shapes } from "jointjs";

export function initJointJs(config: {
  el: string;
  backgroundColor: string;
  height: string;
  width: string;
  isPreventLinkFromInputPorts?: boolean;
  isPreventLinkFromOutputToInputWithinOneElement?: boolean;
  isPreventLinkToOutputPorts?: boolean;
}) {
  const namespace = shapes;
  const graph = new dia.Graph({}, { cellNamespace: namespace });

  const paper = new dia.Paper({
    el: document.getElementById(config.el),
    model: graph,
    height: config.height,
    width: config.width,
    gridSize: 10,
    drawGrid: true,
    background: {
      color: config.backgroundColor
    },
    snapLabels: true,
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
    defaultLink: () => new PrimaryLink(),
    defaultConnectionPoint: { name: "boundary" },
    validateConnection: function (cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
      if (config.isPreventLinkFromInputPorts || false) {
        if (magnetS && magnetS.getAttribute("port-group") === "in") return false;
      }

      if (config.isPreventLinkFromOutputToInputWithinOneElement || true) {
        if (cellViewS === cellViewT) return false;
      }

      if (config.isPreventLinkToOutputPorts || false) {
        return magnetT && magnetT.getAttribute("port-group") === "in";
      }

      return true;
    },
    markAvailable: true,
    snapLinks: { radius: 5 },
    efaultConnectionPoint: { name: "anchor" }
  });

  return {
    paper,
    graph
  };
}
