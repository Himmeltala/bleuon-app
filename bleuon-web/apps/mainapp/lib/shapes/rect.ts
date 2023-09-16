import { dia } from "jointjs";
import { BaseShape } from "./base";
import { PRIMARY_RECTANGLE } from "../constants/key-vals";

/**
 * 基础正方形
 */
const PrimaryRectangleShape = BaseShape.define(
  PRIMARY_RECTANGLE,
  {},
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
