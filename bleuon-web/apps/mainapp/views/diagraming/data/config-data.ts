export const linkConnectorOptions = [
  {
    value: "straight",
    label: "straight"
  },
  {
    value: "jumpover",
    label: "jumpover"
  },
  {
    value: "normal",
    label: "normal"
  },
  {
    value: "rounded",
    label: "rounded"
  },
  {
    value: "smooth",
    label: "smooth"
  },
  {
    value: "curve",
    label: "curve"
  }
];

export const linkRouterOptions = [
  {
    value: "manhattan",
    label: "manhattan"
  },
  {
    value: "metro",
    label: "metro"
  },
  {
    value: "normal",
    label: "normal"
  },
  {
    value: "orthogonal",
    label: "orthogonal"
  },
  {
    value: "rightAngle",
    label: "rightAngle"
  }
];

/**
 * 路由配置
 */
export const linkRouterConfig = ref({
  name: "normal"
});

/**
 * 连接配置
 */
export const linkConnectorConfig = ref({
  name: "normal"
});

/**
 * 上次点击的图形，包含图形 view 和图形下的 model
 */
export const clickedLastElementView = ref({
  view: null,
  model: null
});
