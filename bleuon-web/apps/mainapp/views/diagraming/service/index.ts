/**
 * @description Diagraming 业务层
 * @author 郑人滏 42020306
 * @since 2023/9/18
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, createPrimaryRectangle } from "@mainapp/lib";
import { getLinkOrElementAttr } from "./utils";

/**
 * 在创建图形时还会有其他更多的业务处理
 *
 * @param graph
 */
export function addPrimaryRectangle(graph: dia.Graph) {
  createPrimaryRectangle(graph, { x: 30, y: 30 });
}

/**
 * 修改路由样式
 *
 * @param config
 * @param paper
 */
export function changeLinkRouterConfig(config: Ref<any>, paper: dia.Paper) {
  if (!paper?.options?.defaultRouter) return;
  paper.options.defaultRouter = config.value;
}

/**
 * 修改连线样式
 *
 * @param config
 * @param paper
 */
export function changeConnectorConfig(config: Ref<any>, paper: dia.Paper) {
  if (!paper?.options?.defaultConnector) return;
  paper.options.defaultConnector = config.value;
}

/**
 * 安装 Link 工具
 *
 * @param currView 当前点击的 Link
 */
export function installLinkTools(
  currView: dia.LinkView,
  clickedLastView: Ref<{ model: any; view: dia.LinkView }>
) {
  if (clickedLastView.value.model) {
    clickedLastView.value.model.removeTools(clickedLastView.value.view);
  }

  // @ts-ignore
  currView.model.addTools(currView);

  clickedLastView.value = {
    // @ts-ignore
    model: currView.model,
    view: currView
  };
}

/**
 * 卸载 Link 工具
 *
 * @param clickedLastView 上一次点击的 Link
 */
export function uninstallLinkTools(clickedLastView: Ref<{ model: any; view: dia.ElementView }>) {
  if (clickedLastView.value.model) {
    clickedLastView.value.model.removeTools(clickedLastView.value.view);
  }

  clickedLastView.value = {
    model: null,
    view: null
  };
}

/**
 * 安装图形工具
 *
 * @param currView 当前点击的图形
 * @param clickedLastView 保存当前点击的图形作为上一次点击的图形
 */
export function installShapeTools(
  currView: dia.ElementView,
  clickedLastView: Ref<{ model: any; view: dia.ElementView }>
) {
  if (clickedLastView.value.model) {
    clickedLastView.value.model.removeTools(clickedLastView.value.view);
  }

  // @ts-ignore
  currView.model.addTools(currView);

  clickedLastView.value = {
    // @ts-ignore
    model: currView.model,
    view: currView
  };
}

/**
 * 卸载图形工具
 *
 * @param clickedLastView 上一次点击的图形
 */
export function uninstallShapeTools(clickedLastView: Ref<{ model: any; view: dia.ElementView }>) {
  if (clickedLastView.value.model) {
    clickedLastView.value.model.removeTools(clickedLastView.value.view);
  }

  clickedLastView.value = {
    model: null,
    view: null
  };
}

/**
 * 更新图形文本内容
 *
 * @param currView 点击的图形
 * @param input 输入框 DOM 对象
 */
export function updateShapeText(currView: dia.ElementView | dia.LinkView, input: HTMLInputElement) {
  // @ts-ignore
  const { model } = currView;
  if (model?.updateText) {
    model.updateText(currView, input);
  }
}

/**
 * 更新 Link 文本内容
 *
 * @param currView 点击的 Link
 * @param input 输入框 DOM 对象
 */
export function updateLinkText(currView: dia.LinkView, input: HTMLInputElement) {
  updateShapeText(currView, input);
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
export function changeTextColor(currView: dia.ElementView, color: string) {
  // @ts-ignore
  const model = currView.model;
  model.attr("label/fill", color);
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
  model.attr("label/fontSize", size);
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
  model.attr("label/fontFamily", family);
}

/**
 * 修改连线粗细
 *
 * @param currView 点击的图形
 * @param strokeWidth
 */
export function changeShapeStrokeWidth(currView: dia.ElementView, strokeWidth: number) {
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
export function changeShapeBorderStyle(
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
export function changeShapeBackground(currView: dia.ElementView, color: string) {
  // @ts-ignore
  const model = currView.model;
  model.attr("body/fill", color);
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

/**
 * 关闭颜色选择器
 */
export function closeColorPicker(el: any) {
  setTimeout(() => {
    el.hide();
  }, 200);
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
