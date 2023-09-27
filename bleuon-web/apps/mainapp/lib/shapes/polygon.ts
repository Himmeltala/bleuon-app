/**
 * @description 定义菱形
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes, elementTools } from "jointjs";
import { NormalResizeTool, RotateTool, getPorts, updateLabelText } from "../tools";

/**
 * Primary 圆形
 */
const PrimaryPolygon = shapes.standard.Polygon.define(
  "PrimaryPolygon",
  {
    attrs: {
      body: {
        width: "calc(w)",
        height: "calc(h)",
        fill: "#fcfcfc",
        stroke: "#333333",
        strokeWidth: 1.5,
        cursor: "grab",
        "stroke-dasharray": "none",
        refPoints: "0,10 10,0 20,10 10,20"
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
          name: "polygon-tools",
          tools: [boundaryTool, resizeTool, rotateTool]
        })
      );
    },
    removeTools(elementView: dia.ElementView) {
      if (elementView.hasTools("polygon-tools")) {
        elementView.removeTools();
      }
    }
  }
);

/**
 * 创建基础 polygon
 *
 * @param graph
 * @param config
 */
export function createPrimaryPolygon(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
  }
) {
  const polygon = new PrimaryPolygon({
    position: { x: config?.x || 30, y: config?.y || 30 },
    ports: {
      groups: getPorts()
    }
  });

  polygon.resize(config?.width || 100, config?.height || 80);
  polygon.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(polygon);
}
