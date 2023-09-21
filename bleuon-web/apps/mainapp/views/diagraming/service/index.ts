import { dia, createPrimaryRectangle } from "@mainapp/lib";

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
  paper.options.defaultRouter = config.value;
}

/**
 * 修改连线样式
 *
 * @param config
 * @param paper
 */
export function changeConnectorConfig(config: Ref<any>, paper: dia.Paper) {
  paper.options.defaultConnector = config.value;
}

/**
 * 安装 Link 工具
 *
 * @param view
 */
export function insLinkTools(view: any) {
  // @ts-ignore
  const { model } = view;
  if (model?.addTools) {
    model.addTools(view);
  }
}

/**
 * 卸载 Link 工具
 *
 * @param view
 */
export function uniLinkTools(view: any) {
  // @ts-ignore
  const { model } = view;
  if (model?.removeTools) {
    model.removeTools(view);
  }
}

/**
 * 安装图形工具
 *
 * @param currView 当前点击的图形
 * @param clickedLastView 保存当前点击的图形作为上一次点击的图形
 */
export function insShapeTools(
  currView: dia.ElementView,
  clickedLastView: Ref<{ model: any; view: dia.ElementView }>
) {
  // @ts-ignore
  const { model } = currView;
  if (clickedLastView.value.model) {
    clickedLastView.value.model.removeTools(clickedLastView.value.view);
  }

  model.addTools(currView);

  clickedLastView.value = {
    model,
    view: currView
  };
}

/**
 * 卸载图形工具
 *
 * @param clickedLastView 上一次点击的图形
 */
export function uniShapeTools(clickedLastView: Ref<{ model: any; view: dia.ElementView }>) {
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
 * @param input
 */
export function updateShapeText(currView: dia.ElementView, input: HTMLInputElement) {
  // @ts-ignore
  const { model } = currView;
  if (model?.updateText) {
    model.updateText(currView, input);
  }
}

/**
 * 修改点击的文本粗细
 *
 * @param currView 点击的图形
 */
export function changeTextBold(currView: dia.ElementView) {
  // @ts-ignore
  const model = currView.model;
  const weight = model.attr("label/fontWeight");

  if (weight === "bold") {
    model.attr("label/fontWeight", "normal");
  } else {
    model.attr("label/fontWeight", "bold");
  }
}

/**
 * 修改点击的文本斜体
 *
 * @param currView 点击的图形
 */
export function changeTextItalic(currView: dia.ElementView) {
  // @ts-ignore
  const model = currView.model;
  const style = model.attr("label/fontStyle");

  if (style === "italic") {
    model.attr("label/fontStyle", "normal");
  } else {
    model.attr("label/fontStyle", "italic");
  }
}

/**
 * 修改点击的文本下划线
 *
 * @param currView 点击的图形
 */
export function changeTextUnderline(currView: dia.ElementView) {
  // @ts-ignore
  const model = currView.model;
  const style = model.attr("label/textDecoration");

  if (style === "underline") {
    model.attr("label/textDecoration", "normal");
  } else {
    model.attr("label/textDecoration", "underline");
  }
}

/**
 * 修改点击的文本颜色
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
 * 修改文本大小
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
 * 修改边框粗细
 *
 * @param currView 点击的图形
 * @param strokeWidth
 */
export function changeShapeStrokeWidth(currView: dia.ElementView, strokeWidth: number) {
  // @ts-ignore
  const model = currView.model;
  model.attr("body/strokeWidth", strokeWidth);
}

/**
 * 修改边框样式
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
  if (style === "solid") {
    model.attr("body/strokeDasharray", "none");
  } else if (style === "dashed") {
    model.attr("body/strokeDasharray", "5,2");
  } else if (style === "dotted") {
    model.attr("body/strokeDasharray", "1,2");
  } else {
    model.attr("body/strokeDasharray", "5,2,1,2");
  }
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
 * 修改点击的文本颜色
 *
 * @param currView 点击的连线
 * @param color rgb、十六进制、rgba
 */
export function changeLinkColor(currView: dia.ElementView, color: string) {
  // @ts-ignore
}
