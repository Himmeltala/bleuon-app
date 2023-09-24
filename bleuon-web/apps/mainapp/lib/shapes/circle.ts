/**
 * @description 定义圆形
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes, elementTools } from "jointjs";
import { CircleResizeTool, RotateTool } from "../eletools";
import { PRIMARY_CIRCLE } from "../constants/key-vals";

/**
 * Primary 圆形
 */
const PrimaryCircle = shapes.standard.Ellipse.define(
  PRIMARY_CIRCLE,
  {},
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

export function createPrimaryCircle(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
  }
) {
  const port = {
    attrs: {
      body: {
        magnet: true,
        r: 5
      }
    },
    markup: [
      {
        tagName: "circle",
        selector: "body"
      }
    ]
  };

  const topPort = Object.assign({ position: { name: "top" } }, port);
  const bottomPort = Object.assign({ position: { name: "bottom" } }, port);
  const leftPort = Object.assign({ position: { name: "left" } }, port);
  const rightPort = Object.assign({ position: { name: "right" } }, port);

  const circle = new PrimaryCircle({
    position: { x: config?.x || 30, y: config?.y || 30 },
    ports: {
      groups: {
        top: topPort,
        bottom: bottomPort,
        left: leftPort,
        right: rightPort
      }
    }
  });

  circle.resize(config?.width || 80, config?.height || 80);
  circle.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(circle);
}
