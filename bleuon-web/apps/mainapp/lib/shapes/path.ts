/**
 * @description 定义路径
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */
import { dia, shapes, elementTools } from "jointjs";
import { NormalResizeTool, RotateTool, getPorts, updateLabelText } from "../eletools";

const PrimaryPath = shapes.standard.Path.define(
  "PrimaryPath",
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
          name: "path-tools",
          tools: [boundaryTool, resizeTool, rotateTool]
        })
      );
    },
    removeTools(elementView: dia.ElementView) {
      if (elementView.hasTools("path-tools")) {
        elementView.removeTools();
      }
    }
  }
);

/**
 * 创建 path
 *
 * @param graph
 * @param config
 */
export function createPrimaryPath(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
    refD?: string;
  }
) {
  const polygon = new PrimaryPath({
    position: { x: config?.x || 30, y: config?.y || 30 },
    ports: {
      groups: getPorts()
    },
    attrs: {
      body: {
        refD: config?.refD || ""
      }
    }
  });

  polygon.resize(config?.width || 140, config?.height || 70);
  polygon.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(polygon);
}
