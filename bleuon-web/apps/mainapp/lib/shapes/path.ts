/**
 * @description 定义路径
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */
import { dia, shapes } from "jointjs";
import { getPorts } from "../tools";

/**
 * 创建 path
 *
 * @param graph
 * @param config
 */
export function create(
  graph: dia.Graph,
  config: {
    x?: number;
    y?: number;
    width?: number;
    height?: number;
    attrs?: any;
  }
) {
  const keys = Object.keys(config.attrs || {});
  const markup = keys.map(v => ({ tagName: "path", selector: `${v}` }));

  // @ts-ignore
  const path = new shapes.bleuon.Path({
    position: { x: config.x || 30, y: config.y || 30 },
    ports: {
      groups: getPorts()
    },
    attrs: { ...(config.attrs || {}) },
    markup: [
      ...markup,
      {
        tagName: "text",
        selector: "label"
      }
    ]
  });

  path.resize(config.width || 140, config.height || 70);
  path.addPorts([{ group: "top" }, { group: "bottom" }, { group: "left" }, { group: "right" }]);

  graph.addCell(path);
}
