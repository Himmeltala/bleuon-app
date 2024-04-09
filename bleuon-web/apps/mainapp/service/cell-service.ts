/**
 * @description link 以及 element 共同业务
 * @author zheng
 * @since 2023/9/24
 */

import { dia } from "jointjs";
import { getLinkOrElementAttr } from "./utils";

/**
 * 修改路由样式
 *
 * @param config
 * @param paper
 */
export function changeRouterConfig(config: any, paper: dia.Paper) {
  paper.options.defaultRouter = config;
}

/**
 * 修改连线样式
 *
 * @param config
 * @param paper
 */
export function changeConnectorConfig(config: any, paper: dia.Paper) {
  paper.options.defaultConnector = config;
}

/**
 * 修改网格大小
 *
 * @param size
 * @param paper
 */
export function changeGridSize(size: number, paper: dia.Paper) {
  paper.setGridSize(size);
}

/**
 * 修改文本粗细
 *
 * @param currView 点击的图形
 */
export function changeTextBold(currView: dia.ElementView) {
  // @ts-ignore
  const model = currView.model;
  const isLink = model.isLink();
  let weight = "normal";

  if (isLink) {
    weight = model.label(0).attrs.text.fontWeight || "normal";
  } else {
    weight = model.attr("label/fontWeight");
  }

  if (weight === "bold") {
    weight = "normal";
  } else weight = "bold";

  if (isLink) {
    model.label(0, {
      attrs: {
        text: {
          fontWeight: weight
        }
      }
    });
  } else {
    model.attr("label/fontWeight", weight);
  }
}

/**
 * 修改文本斜体
 *
 * @param currView 点击的图形
 */
export function changeTextItalic(currView: dia.ElementView) {
  // @ts-ignore
  const model = currView.model;
  const isLink = model.isLink();

  let style = "normal";

  if (isLink) {
    style = model.label(0).attrs.text.fontStyle || "normal";
  } else {
    style = model.attr("label/fontStyle");
  }

  if (style === "italic") {
    style = "normal";
  } else style = "italic";

  if (isLink) {
    model.label(0, {
      attrs: {
        text: {
          fontStyle: style
        }
      }
    });
  } else {
    model.attr("label/fontStyle", style);
  }
}

/**
 * 修改文本下划线
 *
 * @param currView 点击的图形
 */
export function changeTextUnderline(currView: dia.ElementView) {
  // @ts-ignore
  const model = currView.model;
  const isLink = model.isLink();

  let style = "normal";

  if (isLink) {
    style = model.label(0).attrs.text.textDecoration || "normal";
  } else {
    style = model.attr("label/textDecoration");
  }

  if (style === "underline") {
    style = "normal";
  } else style = "underline";

  if (isLink) {
    model.label(0, {
      attrs: {
        text: {
          textDecoration: style
        }
      }
    });
  } else {
    model.attr("label/textDecoration", style);
  }
}

/**
 * 修改文本颜色
 *
 * @param currView 点击的图形
 * @param color
 */
export function changeTextColor(currView: dia.ElementView, color: any) {
  // @ts-ignore
  const model = currView.model;
  const isLink = model.isLink();

  if (isLink) {
    model.label(0, {
      attrs: {
        text: {
          fill: color
        }
      }
    });
  } else {
    model.attr("label/fill", color);
  }
}

/**
 * 修改字号
 *
 * @param currView 点击的图形
 * @param size
 */
export function changeTextSize(currView: dia.ElementView, size: number) {
  // @ts-ignore
  const model = currView.model;
  const isLink = model.isLink();

  if (isLink) {
    model.label(0, {
      attrs: {
        text: {
          fontSize: size
        }
      }
    });
  } else {
    model.attr("label/fontSize", size);
  }
}

/**
 * 修改字体
 *
 * @param currView 点击的图形
 * @param family
 */
export function changeTextFamily(currView: dia.ElementView, family: string) {
  // @ts-ignore
  const model = currView.model;
  const isLink = model.isLink();

  if (isLink) {
    model.label(0, {
      attrs: {
        text: {
          fontFamily: family
        }
      }
    });
  } else {
    model.attr("label/fontFamily", family);
  }
}

/**
 * 修改连线粗细
 *
 * @param currView 点击的图形
 * @param strokeWidth
 */
export function changeStrokeWidth(currView: dia.ElementView, strokeWidth: number) {
  // @ts-ignore
  const model = currView.model;

  const attrKey = getLinkOrElementAttr(model, "strokeWidth");
  model.attr(attrKey, strokeWidth);
}

/**
 * 修改连线样式
 *
 * @param currView 点击的图形
 * @param style "solid" | "dashed" | "dotted" | "dashed-dotted"
 */
export function changeBorderStyle(
  currView: dia.ElementView,
  style: "solid" | "dashed" | "dotted" | "dashed-dotted"
) {
  // @ts-ignore
  const model = currView.model;
  const attrKey = getLinkOrElementAttr(model, "strokeDasharray");

  let lattice = "none";

  if (style === "dashed-dotted") {
    lattice = "5,2,1,2";
  } else if (style === "dotted") {
    lattice = "1,2";
  } else if (style === "dashed") {
    lattice = "5,2";
  } else {
    lattice = "none";
  }

  model.attr(attrKey, lattice);
}

/**
 * 修改图形背景颜色
 *
 * @param currView 点击的图形
 * @param color rgb、十六进制、rgba
 */
export function changeBackground(currView: dia.ElementView, color: string) {
  // @ts-ignore
  const model = currView.model;
  model.attr("body/fill", color);
}

/**
 * 修改 Link 色
 *
 * @param currView 点击的连线
 * @param color rgb、十六进制、rgba
 */
export function changeLinkColor(currView: dia.ElementView, color: string) {
  // @ts-ignore
  const model = currView.model;

  const attrKey = getLinkOrElementAttr(model, "stroke");
  model.attr(attrKey, color);
}

/**
 * 关闭颜色选择器
 */
export function closeColorPicker(el: any) {
  setTimeout(() => {
    el.hide();
  }, 200);
}

/**
 * 打开颜色选择器
 *
 * @param el ref 模板引用
 */
export function openColorPicker(el: any) {
  setTimeout(() => {
    el.show();
  }, 100);
}
