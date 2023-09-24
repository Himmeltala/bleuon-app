/**
 * @description 定义圆形
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes, elementTools } from "jointjs";
import { CircleResizeTool, RotateTool, getPorts, updateLabelText } from "../eletools";

/**
 * Primary 圆形
 */
const PrimaryCircle = shapes.standard.Ellipse.define(
  "PrimaryCircle",
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
      const resizeTool = new CircleResizeTool();
      const rotateTool = new RotateTool();

      elementView.addTools(
        new dia.ToolsView({
          name: "circle-tools",
          tools: [boundaryTool, resizeTool, rotateTool]
        })
      );
    },
    removeTools(elementView: dia.ElementView) {
      if (elementView.hasTools("circle-tools")) {
        elementView.removeTools();
      }
    }
  }
);

/**
 * 创建基础 circle
 * 
 * @param graph 
 * @param config 
 */
export function createPrimaryCircle(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
  }
) {
  const circle = new PrimaryCircle({
    position: { x: config?.x || 30, y: config?.y || 30 },
    ports: {
      groups: getPorts()
    }
  });

  circle.resize(config?.width || 80, config?.height || 80);
  circle.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(circle);
}
