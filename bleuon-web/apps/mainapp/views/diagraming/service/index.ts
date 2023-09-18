import { dia, createPrimaryRectangle } from "@mainapp/lib";

/**
 * 在创建图形时还会有其他更多的业务处理
 *
 * @param graph
 */
export function addPrimaryRectangle(graph: dia.Graph) {
  createPrimaryRectangle(graph, { x: 30, y: 30 });
}

export function watchLinkRouterConfig(config: Ref<any>, paper: dia.Paper) {
  return watch(config, (newVal, oldVal) => {
    paper.options.defaultRouter = newVal;
  });
}

export function watchLinkConnectorConfig(config: Ref<any>, paper: dia.Paper) {
  return watch(config, (newVal, oldVal) => {
    paper.options.defaultConnector = newVal;
  });
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
 * @param view
 * @param clickedLastView
 */
export function insShapeTools(
  view: dia.ElementView,
  clickedLastView: Ref<{ model: any; view: dia.ElementView }>
) {
  // @ts-ignore
  const { model } = view;
  if (clickedLastView.value.model) {
    clickedLastView.value.model.removeTools(clickedLastView.value.view);
  }

  model.addTools(view);

  clickedLastView.value = {
    model,
    view
  };
}

/**
 * 卸载图形工具
 *
 * @param lastView
 */
export function uniShapeTools(lastView: Ref<{ model: any; view: dia.ElementView }>) {
  if (lastView.value.model) {
    lastView.value.model.removeTools(lastView.value.view);
  }

  lastView.value = {
    model: null,
    view: null
  };
}

/**
 * 更新图形文本内容
 *
 * @param view
 * @param input
 */
export function updateShapeText(view: dia.ElementView, input: HTMLInputElement) {
  // @ts-ignore
  const { model } = view;
  if (model?.updateText) {
    model.updateText(view, input);
  }
}

/**
 * 修改点击的文本粗细
 *
 * @param currView
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
 * @param currView
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
 * @param currView
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
 * @param currView
 */
export function changeTextColor(currView: dia.ElementView, color: string) {
  // @ts-ignore
  const model = currView.model;
  model.attr("label/fill", color);
}
