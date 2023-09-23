/**
 * @description 定义矩形
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "jointjs";
import { BaseShape } from "./base";
import { PRIMARY_RECTANGLE } from "../constants/key-vals";

/**
 * Primary 矩形
 */
const PrimaryRectangle = BaseShape.define(
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
 * 创建 Primary 矩形
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

  const primaryRectangle = new PrimaryRectangle({
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
