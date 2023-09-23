/**
 * @description Diagraming 数据
 * @author 郑人滏 42020306
 * @since 2023/9/18
 * @link https://github.com/himmelbleu/bleuon-app
 */

/**
 * Link 两端样式下拉框
 */
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
 * Link 两端连接配置
 */
export const linkConnectorConfig = ref({
  name: "normal"
});

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
 * 点击的上一次图形 View，包含图形 View 对象和图形的 model 对象
 */
export const clickedLastView = ref({
  view: null,
  model: null
});

/**
 * 点击的当前图形 View
 */
export const clickedCurrView = ref(null);

/**
 * 字体颜色
 */
export const textColor = ref("");

/**
 * 字体大小
 */
export const textSize = ref(14);

/**
 * 字体样式
 */
export const fontFamilyOptions = [
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

// 文本字体
export const fontFamily = ref("微软雅黑");

/**
 * 图形边框线条粗细
 */
export const shapeStrokeWidthOptions = [
  {
    value: 0,
    label: "0px"
  },
  {
    value: 0.5,
    label: "0.5px"
  },
  {
    value: 1,
    label: "1px"
  },
  {
    value: 1.5,
    label: "1.5px"
  },
  {
    value: 2,
    label: "2px"
  },
  {
    value: 3,
    label: "3px"
  },
  {
    value: 4,
    label: "4px"
  },
  {
    value: 5,
    label: "5px"
  },
  {
    value: 6,
    label: "6px"
  },
  {
    value: 8,
    label: "8px"
  },
  {
    value: 10,
    label: "10px"
  }
];

// 图形 stroke 宽度
export const shapeStrokeWidth = ref(1.5);
// 图形背景颜色
export const shapeBackground = ref("white");
// 图形 stroke 样式
export const shapeBorderStyle = ref("solid");

// 定义 paper 缩放比例
export const currentScale = ref(1);
// 定义 paper 水平偏移量
export const currentOffsetX = ref(0);
// 定义 paper 垂直偏移量
export const currentOffsetY = ref(0);
// 是否点击了 Link 元素
export const isClickedLink = ref(false);
// 是否点击了 Element 元素
export const isClickedElement = ref(false);
