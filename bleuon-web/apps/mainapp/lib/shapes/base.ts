import { dia, elementTools } from "jointjs";
import { BASE_SHAPE } from "../constants/key-vals";
import { editCellViewText } from "../tools";

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
        strokeWidth: 2,
        cursor: "grab"
      },
      label: {
        text: "",
        textVerticalAnchor: "middle",
        textAnchor: "middle",
        fill: "#333333",
        fontSize: 18,
        pointerEvents: "none",
        refX: "50%",
        refY: "50%"
      }
    }
  },
  {
    updateText(cellView: dia.CellView, eleInput: Ref<HTMLInputElement>) {
      editCellViewText(cellView, eleInput);
    },
    addTools(cellView: dia.CellView) {
      cellView.addTools(
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
    removeTools(cellView: dia.CellView) {
      if (cellView.hasTools("resize-tools")) {
        cellView.removeTools();
      }
    }
  }
);
