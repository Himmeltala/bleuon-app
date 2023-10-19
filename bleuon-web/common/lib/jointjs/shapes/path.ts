/**
 * @description 定义路径
 * @author zheng
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */
import { dia, elementTools } from "jointjs";
import { upgradeLabelText } from "../utils";
import { NormalResizeTool, RotateTool } from "../tools";

const Path = dia.Element.define(
  "bleuon.Path",
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
    upgradeLabelText,
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

export default Path;
