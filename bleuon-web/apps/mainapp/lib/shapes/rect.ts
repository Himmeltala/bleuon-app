/**
 * @description 定义矩形
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes, elementTools } from "jointjs";
import { NormalResizeTool, RotateTool, getPorts, updateLabelText } from "../tools";

/**
 * 所有图形的基础
 */
export const PrimaryRect = shapes.standard.Rectangle.define(
  "PrimaryRect",
  {
    attrs: {
      body: {
        width: "calc(w)",
        height: "calc(h)",
        fill: "#fcfcfc",
        stroke: "#333333",
        strokeWidth: 1.5,
        cursor: "grab",
        "stroke-dasharray": "none"
      },
      label: {
        text: "",
        textVerticalAnchor: "middle",
        textAnchor: "middle",
        fill: "#333333",
        fontWeight: "normal",
        fontSize: 18,
        fontFamily: "微软雅黑",
        refX: "50%",
        refY: "50%"
      }
    }
  },
  {
    updateLabelText,
    addTools(elementView: dia.ElementView) {
      const boundaryTool = new elementTools.Boundary();
      const resizeTool = new NormalResizeTool();
      const rotateTool = new RotateTool();

      elementView.addTools(
        new dia.ToolsView({
          name: "rect-tools",
          tools: [boundaryTool, resizeTool, rotateTool]
        })
      );
    },
    removeTools(elementView: dia.ElementView) {
      if (elementView.hasTools("rect-tools")) {
        elementView.removeTools();
      }
    }
  }
);

/**
 * 创建基础 rect
 *
 * @param graph joint.Graph 对象
 * @param config 图形配置项
 */
export function createPrimaryRectangle(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
    attrs?: {
      body: any;
    };
  }
) {
  const rect = new PrimaryRect({
    position: { x: config?.x || 30, y: config?.y || 30 },
    ports: {
      groups: getPorts()
    },
    attrs: config?.attrs || {}
  });

  rect.resize(config?.width || 140, config?.height || 70);
  rect.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(rect);
}
