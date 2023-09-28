/**
 * @description 定义 jointjs elementTools
 * @author 郑人滏 42020306
 * @since 2023/9/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, elementTools } from "jointjs";
import { formatted } from "@common/utils/date";

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
          cursor: "nwse-resize",
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
    // 计算 x,y 点到原点(0,0)的直角坐标系中的角度
    const degrees = Math.atan2(coordinates.x, coordinates.y);
    model.rotate(degrees);
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

/**
 * 更新 label 文本
 *
 * @param elementView
 * @param textInput
 */
export function updateLabelText(elementView: dia.ElementView, textInput: HTMLInputElement) {
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
}

/**
 * 导出 SVG 为图片
 *
 * @param el svg
 * @param type png、jpeg
 */
export function convertSvgToImage(
  paper: dia.Paper,
  graph: dia.Graph,
  type: "png" | "jpeg",
  config: { width: number; height: number; fileName: string; fill?: string }
) {
  const canvas = document.createElement("canvas");
  const context = canvas.getContext("2d");

  const elements = graph.getElements();
  elements.forEach(v => {
    v.removePorts();
  });

  const dataUri = new XMLSerializer().serializeToString(paper.childNodes.svg);
  const image = new Image();
  image.src = "data:image/svg+xml," + encodeURIComponent(dataUri);

  image.onload = function () {
    canvas.width = config.width;
    canvas.height = config.height;
    context.fillStyle = config.fill || "#ffffff";
    context.fillRect(0, 0, canvas.width, canvas.height);
    context.drawImage(image, 0, 0);

    const dataURL = canvas.toDataURL(`image/${type}`);
    const link = document.createElement("a");
    link.href = dataURL;
    link.download = `${config.fileName}-${formatted("yyyy-MM-dd HH_mm_ss")}.${type}`;
    link.click();
  };
}
