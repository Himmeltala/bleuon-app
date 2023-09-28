/**
 * @description 定义路径
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */
import { dia, shapes, elementTools } from "jointjs";
import { NormalResizeTool, RotateTool, getPorts, updateLabelText } from "../tools";

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
    addClickedTools(elementView: dia.ElementView) {
      const boundaryTool = new elementTools.Boundary();
      const resizeTool = new NormalResizeTool();
      const rotateTool = new RotateTool();

      elementView.addTools(
        new dia.ToolsView({
          name: "path-clicked-tools",
          tools: [boundaryTool, resizeTool, rotateTool]
        })
      );
    },
    removeClickedTools(elementView: dia.ElementView) {
      if (elementView.hasTools("path-clicked-tools")) {
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
export function create(
  graph: dia.Graph,
  config: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
    attrs?: any;
  }
) {
  const keys = Object.keys(config.attrs || {});
  const markup = keys.map(v => ({ tagName: "path", selector: `${v}` }));

  const path = new PrimaryPath({
    position: { x: config.x || 30, y: config.y || 30 },
    ports: {
      groups: getPorts()
    },
    attrs: { ...(config.attrs || {}) },
    markup: [
      ...markup,
      {
        tagName: "text",
        selector: "label"
      }
    ]
  });

  path.resize(config.width || 140, config.height || 70);
  path.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(path);
}
