/**
 * @description 定义 jointjs 基础图形
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes, elementTools, linkTools } from "jointjs";
import { BASE_SHAPE, BASE_LINK } from "../constants/key-vals";
import { ResizeTool, RotateTool } from "../eletools";

/**
 * 所有图形的基础
 */
export const BaseShape = dia.Element.define(
  BASE_SHAPE,
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
    updateText(elementView: dia.ElementView, textInput: HTMLInputElement) {
      // @ts-ignore
      const { model } = elementView;
      const { position, size } = model.attributes;

      const cellText = model.attr("label/text");
      textInput.value = cellText;

      textInput.style.top = position.y + "px";
      textInput.style.left = position.x + "px";
      textInput.style.width = size.width + "px";
      textInput.style.height = size.height + "px";
      textInput.style.display = "block";

      function handleKeydownEvent(event: any) {
        if (event.key === "Enter") {
          let newCellText = textInput.value;
          model.attr("label/text", newCellText);
          textInput.style.display = "none";
          textInput.removeEventListener("keydown", handleKeydownEvent);
        }
      }

      textInput.value = "";
      textInput.addEventListener("keydown", handleKeydownEvent);
      textInput.focus();
    },
    addTools(elementView: dia.ElementView) {
      const boundaryTool = new elementTools.Boundary();
      const resizeTool = new ResizeTool();
      const rotateTool = new RotateTool();

      elementView.addTools(
        new dia.ToolsView({
          name: "shape-tools",
          tools: [boundaryTool, resizeTool, rotateTool]
        })
      );
    },
    removeTools(elementView: dia.ElementView) {
      if (elementView.hasTools("shape-tools")) {
        elementView.removeTools();
      }
    }
  }
);

/**
 * 所有 Link 的基础
 */
export const BaseLink = shapes.standard.Link.define(
  BASE_LINK,
  {
    router: { name: "manhattan" },
    attrs: {
      line: {
        fill: "none",
        stroke: "#333333",
        strokeWidth: 2
      }
    }
  },
  {
    updateText(linkView: dia.LinkView, textInput: HTMLInputElement) {
      // @ts-ignore
      const { model } = linkView;
      const label = model.label(0);
      const cellText = label?.attrs?.text?.text || "";

      const bbox = linkView.getBBox();
      const centerX = bbox.x + bbox.width / 2;
      const centerY = bbox.y + bbox.height / 2;
      const oneThirdWidth = bbox.width / 3;
      textInput.value = cellText;

      textInput.style.top = centerY - 10 + "px";
      textInput.style.left = centerX - oneThirdWidth / 2 + "px";
      textInput.style.width = oneThirdWidth + "px";
      textInput.style.height = 20 + "px";
      textInput.style.display = "block";

      function handleKeydownEvent(event: any) {
        if (event.key === "Enter") {
          let newCellText = textInput.value;

          model.label(0, {
            position: 0.5,
            attrs: {
              text: {
                text: newCellText
              }
            }
          });

          textInput.style.display = "none";
          textInput.removeEventListener("keydown", handleKeydownEvent);
        }
      }

      textInput.value = "";
      textInput.addEventListener("keydown", handleKeydownEvent);
      textInput.focus();
    },
    addTools(linkView: any) {
      const tools = [
        // new linkTools.Vertices(),
        new linkTools.SourceArrowhead(),
        new linkTools.TargetArrowhead()
      ];

      linkView.addTools(
        new dia.ToolsView({
          name: "link-tools",
          tools: tools
        })
      );
    },
    removeTools(linkView: any) {
      if (linkView.hasTools("link-tools")) {
        linkView.removeTools();
      }
    }
  }
);
