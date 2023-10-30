/**
 * @description 定义路径
 * @author zheng
 * @since 2023/9/24
 */
import { dia, elementTools } from "jointjs";
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
    upgradeLabelText(elementView: dia.ElementView, input: HTMLInputElement) {
      // @ts-ignore
      const { model } = elementView;

      input.value = model.attr("label/text");

      input.style.top = "0px";
      input.style.left = "0px";
      input.style.width = "200px";
      input.style.height = "100px";
      input.style.display = "block";

      function handleKeydownEvent(event: any) {
        if (event.key === "Enter") {
          model.attr("label/text", input.value);
          input.style.display = "none";
          input.removeEventListener("keydown", handleKeydownEvent);
        } else if (event.key === "Escape") {
          input.style.display = "none";
          input.removeEventListener("keydown", handleKeydownEvent);
        }
      }

      input.value = "";
      input.focus();
      input.addEventListener("keydown", handleKeydownEvent);
    },
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
