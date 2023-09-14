import { dia, shapes, elementTools } from "jointjs";
import { PRIMARY_RECTANGLE } from "../constants/key-vals";

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
 * 基础正方形
 */
const PrimaryRectangleShape = shapes.standard.Rectangle.define(
  PRIMARY_RECTANGLE,
  {
    size: { width: 140, height: 70 },
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
        text: "Hello",
        textVerticalAnchor: "middle",
        textAnchor: "middle",
        fill: "#333333",
        fontSize: 18,
        pointerEvents: "none"
      }
    }
  },
  {
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

/**
 * 生成 ports 配置对象
 */
function generatePortsConfig() {
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

  return { topPort, bottomPort, leftPort, rightPort };
}

/**
 * 创建基础正方形
 *
 * @param graph joint.Graph 对象
 * @param config 图形配置项
 */
export function createPrimaryRectangle(
  graph: dia.Graph,
  config?: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
  }
) {
  const { topPort, bottomPort, leftPort, rightPort } = generatePortsConfig();

  const primaryRectangle = new PrimaryRectangleShape({
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

  primaryRectangle.resize(config?.width || 140, config?.height || 70);

  primaryRectangle.addPorts([
    { group: "top" },
    { group: "bottom" },
    { group: "left" },
    { group: "right" }
  ]);

  primaryRectangle.addTo(graph);
}
