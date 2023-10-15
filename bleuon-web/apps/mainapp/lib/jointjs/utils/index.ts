import { dia } from "jointjs";
import { DateUtil } from "@common/utils";

/**
 * 更新 label 文本
 *
 * @param elementView
 * @param input
 */
export function upgradeLabelText(elementView: dia.ElementView, input: HTMLInputElement) {
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
export function downloadWithXml(
  paper: dia.Paper,
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
export function downloadWithDataUri(data: FlowchartModel, type: "png" | "jpeg") {
  const canvas = document.createElement("canvas");
  const context = canvas.getContext("2d");

  if (!data.dataUri) {
    ElMessage.error("内容为空，不能下载！");
    return;
  }

  const dataUri = data.dataUri;
  const image = new Image();
  image.src = dataUri;

  image.onload = function () {
    canvas.width = data.width;
    canvas.height = data.height;
    context.fillStyle = data.bgColor;
    context.fillRect(0, 0, canvas.width, canvas.height);
    context.drawImage(image, 0, 0);

    const dataURL = canvas.toDataURL(`image/${type}`);
    const link = document.createElement("a");
    link.href = dataURL;
    link.download = `${data.fileName}-${DateUtil.formatted()}.${type}`;
    link.click();
  };
}
