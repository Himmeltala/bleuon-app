/**
 * @description 定义 jointjs elementTools
 * @author zheng
 * @since 2023/9/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { DateUtil } from "@common/utils";
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
 * @param input
 */
export function updateLabelText(elementView: dia.ElementView, input: HTMLInputElement) {
  // @ts-ignore
  const { model } = elementView;
  const { position, size } = model.attributes;

  input.value = model.attr("label/text");

  input.style.top = position.y + "px";
  input.style.left = position.x + "px";
  input.style.width = size.width + "px";
  input.style.height = size.height + "px";
  input.style.display = "block";

  function handleKeydownEvent(event: any) {
    if (event.key === "Enter") {
      let newCellText = input.value;
      model.attr("label/text", newCellText);
      input.style.display = "none";
      input.removeEventListener("keydown", handleKeydownEvent);
    }
  }

  input.value = "";
  input.addEventListener("keydown", handleKeydownEvent);
  input.focus();
}

/**
 * 导出 SVG 为图片
 *
 * @param paper
 * @param graph
 * @param type png、jpeg
 * @param config
 * @param success
 * @param failure
 */
export function convertFlowchartIntoImage(
  paper: dia.Paper,
  graph: dia.Graph,
  type: "png" | "jpeg",
  config: FlowchartModel,
  success?: Function
) {
  const canvas = document.createElement("canvas");
  const context = canvas.getContext("2d");

  const dataUri = getDataUri(paper);
  const image = new Image();
  image.src = dataUri;

  image.onload = function () {
    canvas.width = config.width;
    canvas.height = config.height;
    context.fillStyle = config.bgColor;
    context.fillRect(0, 0, canvas.width, canvas.height);
    context.drawImage(image, 0, 0);

    const dataURL = canvas.toDataURL(`image/${type}`);
    const link = document.createElement("a");
    link.href = dataURL;
    link.download = `${config.fileName}-${DateUtil.formatted()}.${type}`;
    link.click();

    success && success();
  };
}

/**
 * 获取 data uri
 *
 * @param paper
 * @param graph
 */
export function getDataUri(paper: dia.Paper) {
  paper.hideTools();

  const svgCopy = paper.childNodes.svg.cloneNode(true);
  // @ts-ignore
  const circles = svgCopy.querySelectorAll<SVGCircleElement>(".joint-port-body");

  circles.forEach((circle: SVGCircleElement) => {
    circle.style.fill = "transparent";
    circle.style.stroke = "transparent";
    circle.style.strokeWidth = "0";
    circle.style.strokeDasharray = "none";
  });

  const dataUri = new XMLSerializer().serializeToString(svgCopy);
  return "data:image/svg+xml," + encodeURIComponent(dataUri);
}

/**
 * 通过 dataUri 下载图片
 *
 * @param data width、height、bgColor、dataUri
 * @param type png、jpeg
 * @param success
 * @param failure
 */
export function downloadWithDataUri(
  data: { width?: number; height?: number; bgColor?: string; dataUri?: string; fileName?: string },
  type: "png" | "jpeg"
) {
  const canvas = document.createElement("canvas");
  const context = canvas.getContext("2d");

  const image = new Image();

  if (!data.dataUri) {
    return;
  }

  image.src = data.dataUri;

  image.onload = function () {
    canvas.width = data.width || 1000;
    canvas.height = data.height || 1000;
    context.fillStyle = data.bgColor || "#ffffff";
    context.fillRect(0, 0, canvas.width, canvas.height);
    context.drawImage(image, 0, 0);

    const dataURL = canvas.toDataURL(`image/${type}`);
    const link = document.createElement("a");
    link.href = dataURL;
    link.download = `${data.fileName || "未命名的文件"}-${DateUtil.formatted()}.${type}`;
    link.click();
  };
}
