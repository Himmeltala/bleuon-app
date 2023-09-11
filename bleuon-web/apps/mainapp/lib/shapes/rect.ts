import { dia } from "jointjs";
import { BaseShape } from "../init";
import { PRIMARY_RECTANGLE } from "../constants/key-vals";

/**
 * 基础正方形
 */
const PrimaryRectangleShape = BaseShape.define(
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
        text: "",
        textVerticalAnchor: "middle",
        textAnchor: "middle",
        x: "calc(.5*w)",
        y: "calc(.5*h)",
        fill: "#333333",
        fontSize: 18,
        pointerEvents: "none"
      }
    }
  },
  {
    markup: [
      {
        tagName: "rect",
        selector: "body"
      },
      {
        tagName: "text",
        selector: "label"
      }
    ]
  }
);

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
    size: { width: config?.width || 140, height: config?.height || 70 },
    ports: {
      groups: {
        top: topPort,
        bottom: bottomPort,
        left: leftPort,
        right: rightPort
      }
    }
  });

  primaryRectangle.addPorts([
    { group: "top" },
    { group: "bottom" },
    { group: "left" },
    { group: "right" }
  ]);

  primaryRectangle.addTo(graph);
}
