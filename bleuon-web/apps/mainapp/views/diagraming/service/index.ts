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
