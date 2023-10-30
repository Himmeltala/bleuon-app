/**
 * @description jointjs 图形的工具
 * @author zheng
 * @since 2023/9/23
 */

import { dia, elementTools } from "jointjs";

/**
 * Rect 缩放工具
 *
 * @link https://resources.jointjs.com/docs/jointjs/v3.7/joint.html#elementTools.Control
 */
export class NormalResizeTool extends elementTools.Control {
  constructor() {
    super();

    this.children = [
      {
        tagName: "rect",
        selector: "handle",
        attributes: {
          cursor: "nwse-resize",
          width: 10,
          height: 10,
          x: 0,
          y: 0,
          fill: "#001DFF"
        }
      }
    ];
  }

  protected getPosition(view: dia.ElementView): dia.Point {
    // @ts-ignore
    const model = view.model;
    const { width, height } = model.size();
    return { x: width, y: height };
  }

  protected setPosition(view: dia.ElementView, coordinates: dia.Point): void {
    // @ts-ignore
    const model = view.model;
    model.resize(Math.max(coordinates.x - 10, 1), Math.max(coordinates.y - 10, 1));
  }
}

/**
 * Circle 缩放工具
 *
 * @link https://resources.jointjs.com/docs/jointjs/v3.7/joint.html#elementTools.Control
 */
export class CircleResizeTool extends elementTools.Control {
  constructor() {
    super();

    this.children = [
      {
        tagName: "rect",
        selector: "handle",
        attributes: {
          cursor: "nwse-resize",
          width: 10,
          height: 10,
          x: 0,
          y: 0,
          fill: "#001DFF"
        }
      }
    ];
  }

  protected getPosition(view: dia.ElementView): dia.Point {
    // @ts-ignore
    const model = view.model;
    const { width, height } = model.size();
    return { x: width, y: height };
  }

  protected setPosition(view: dia.ElementView, coordinates: dia.Point): void {
    // @ts-ignore
    const model = view.model;
    model.resize(coordinates.x, coordinates.y);
  }
}

/**
 * Shape 旋转工具
 *
 * @link https://resources.jointjs.com/docs/jointjs/v3.7/joint.html#elementTools.Control
 */
// @ts-ignore
export class RotateTool extends elementTools.Control {
  constructor() {
    super();

    this.children = [
      {
        tagName: "rect",
        selector: "handle",
        attributes: {
          cursor: "e-resize",
          width: 10,
          height: 10,
          x: -10,
          y: -10,
          fill: "#333333"
        }
      }
    ];
  }

  protected setPosition(view: dia.ElementView, coordinates: dia.Point): void {
    // @ts-ignore
    const { model } = view;
    const angle = Math.atan2(coordinates.x, coordinates.y);
    model.rotate(angle);
  }
}

export function getPorts() {
  const port = {
    attrs: { body: { magnet: true, r: 5 } },
    markup: [{ tagName: "circle", selector: "body" }]
  };

  const top = Object.assign({ position: { name: "top" } }, port);
  const bottom = Object.assign({ position: { name: "bottom" } }, port);
  const left = Object.assign({ position: { name: "left" } }, port);
  const right = Object.assign({ position: { name: "right" } }, port);

  return { top, bottom, left, right };
}
