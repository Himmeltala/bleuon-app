/**
 * @description 定义菱形
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, shapes, elementTools } from "jointjs";
import { NormalResizeTool, RotateTool } from "../eletools";
import { PRIMARY_POLYGON } from "../constants/key-vals";

/**
 * Primary 圆形
 */
const PrimaryPolygon = shapes.standard.Polygon.define(
  PRIMARY_POLYGON,
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

export function createPrimaryPolygon(
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

  const polygon = new PrimaryPolygon({
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

  polygon.resize(config?.width || 80, config?.height || 80);
  polygon.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(polygon);
}
