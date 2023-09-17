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
 * @param lastView
 */
export function insShapeTools(
  view: dia.ElementView,
  lastView: Ref<{ model: any; view: dia.ElementView }>
) {
  // @ts-ignore
  const { model } = view;
  if (lastView.value.model) {
    lastView.value.model.removeTools(lastView.value.view);
  }

  model.addTools(view);

  lastView.value = {
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
