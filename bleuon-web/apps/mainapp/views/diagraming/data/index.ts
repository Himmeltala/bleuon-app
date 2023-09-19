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

/**
 * Link 路由 name 下拉框
 */
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
 * Link 路由配置
 */
export const linkRouterConfig = ref({
  name: "normal"
});

/**
 * Link 两端连接配置
 */
export const linkConnectorConfig = ref({
  name: "normal"
});

/**
 * 点击的上一次图形 View，包含图形 View 对象和图形的 model 对象
 */
export const clickedLastView = ref({
  view: null,
  model: null
});

/**
 * 点击的当前图形 View
 */
export const clickedCurrView = ref();

/**
 * 字体颜色
 */
export const textColor = ref();

/**
 * 字体大小
 */
export const textSize = ref(14);

/**
 * 字体样式
 */
export const fontFamily = [
  {
    value: "微软雅黑",
    label: "微软雅黑"
  },
  {
    value: "Arial",
    label: "Arial"
  },
  {
    value: "Bahnschrift",
    label: "Bahnschrift"
  },
  {
    value: "BIZ UDGothic",
    label: "BIZ UDGothic"
  },
  {
    value: "Calibri",
    label: "Calibri"
  },
  {
    value: "Cambria",
    label: "Cambria"
  },
  {
    value: "Candara",
    label: "Candara"
  },
  {
    value: "Comic Sans MS",
    label: "Comic Sans MS"
  },
  {
    value: "Consolas",
    label: "Consolas"
  },
  {
    value: "Constantia",
    label: "Constantia"
  },
  {
    value: "Corbel",
    label: "Corbel"
  }
];

export const textStyle = ref("微软雅黑");
