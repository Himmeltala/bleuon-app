/**
 * @description jointjs 辅助工具，一般对外使用
 * @author zheng
 * @since 2023/9/30
 */

import { dia } from "jointjs";
import { DateUtil } from "@common/utils";

/**
 * 将 SVG 字符串转换为图片 URI 形式
 *
 * @param str
 * @param width
 * @param height
 * @param format
 * @returns
 */
export function SVGStrToImageURI(
  str: string,
  config: {
    width: number;
    height: number;
    bgColor: string;
    format: "png" | "jpeg";
  }
): Promise<string> {
  return new Promise(resolve => {
    const canvas = document.createElement("canvas");
    canvas.width = config.width;
    canvas.height = config.height;

    const ctx = canvas.getContext("2d");
    ctx.fillStyle = config.bgColor;
    ctx.fillRect(0, 0, config.width, config.height);

    const image = new Image();
    image.onload = function () {
      ctx.drawImage(image, 0, 0, config.width, config.height);

      const dataURI = canvas.toDataURL(`image/${config.format}`);
      resolve(dataURI);
    };

    image.src = `data:image/svg+xml,${encodeURIComponent(str)}`;
  });
}

/**
 * 获取 data uri
 *
 * @param paper
 * @param graph
 */
export async function getDataURI(paper: dia.Paper) {
  paper.hideTools();

  const SVGEle = paper.childNodes.svg.cloneNode(true) as SVGAElement;
  const circles = SVGEle.querySelectorAll<SVGCircleElement>(".joint-port-body");

  circles.forEach((circle: SVGCircleElement) => {
    circle.style.fill = "transparent";
    circle.style.stroke = "transparent";
    circle.style.strokeWidth = "0";
    circle.style.strokeDasharray = "none";
  });

  const svgStr = new XMLSerializer().serializeToString(SVGEle);
  return await SVGStrToImageURI(svgStr, {
    width: paper.getArea().width,
    height: paper.getArea().height,
    bgColor: paper.options.background.color,
    format: "jpeg"
  });
}

/**
 * 通过 dataUri 下载图片
 *
 * @param data width、height、bgColor、dataUri
 * @param type png、jpeg
 * @param success
 * @param failure
 */
export function downloadWithDataURI(dataURI: string, filename: string, type: "png" | "jpeg") {
  const link = document.createElement("a");
  link.href = dataURI;
  link.download = `${filename}-${DateUtil.formatted()}.${type}`;
  link.click();
}
