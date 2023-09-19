import { dia, elementTools } from "jointjs";
import { BASE_SHAPE } from "../constants/key-vals";

/**
 * Shape 的扩大工具
 */
// @ts-ignore
const ResizeTool = elementTools.Control.extend({
  children: [
    {
      tagName: "rect",
      selector: "handle",
      attributes: {
        cursor: "pointer",
        width: 10,
        height: 10,
        x: -10,
        y: -10
      }
    },
    {
      tagName: "rect",
      selector: "extras",
      attributes: {
        "pointer-events": "none",
        fill: "none",
        stroke: "#33334F",
        "stroke-dasharray": "2,4",
        rx: 5,
        ry: 5
      }
    }
  ],
  getPosition: function (view: any) {
    const model = view.model;
    const { width, height } = model.size();
    return { x: width, y: height };
  },
  setPosition: function (view: any, coordinates: any) {
    const model = view.model;
    model.resize(Math.max(coordinates.x - 10, 1), Math.max(coordinates.y - 10, 1));
  }
});

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
        fill: "#FCFCFC",
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

      textInput.addEventListener("keydown", handleKeydownEvent);

      textInput.focus();
    },
    addTools(elementView: dia.ElementView) {
      elementView.addTools(
        new dia.ToolsView({
          name: "resize-tools",
          tools: [
            new ResizeTool({
              selector: "body"
            })
          ]
        })
      );
    },
    removeTools(elementView: dia.ElementView) {
      if (elementView.hasTools("resize-tools")) {
        elementView.removeTools();
      }
    }
  }
);
