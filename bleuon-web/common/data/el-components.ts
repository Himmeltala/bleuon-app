/**
 * @description element-plus 组件的通用数据
 * @author 郑人滏 42020306
 * @since 2023/10/1
 * @link https://github.com/himmelbleu/bleuon-app
 */

export const shortcuts = [
  {
    text: "明天",
    value: () => {
      const date = new Date();
      date.setTime(date.getTime() + 3600 * 1000 * 24);
      return date;
    }
  },
  {
    text: "一周后",
    value: () => {
      const date = new Date();
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
      return date;
    }
  },
  {
    text: "两周后",
    value: () => {
      const date = new Date();
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 14);
      return date;
    }
  },
  {
    text: "一月后",
    value: () => {
      const date = new Date();
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 30);
      return date;
    }
  },
  {
    text: "两月后",
    value: () => {
      const date = new Date();
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 30);
      return date;
    }
  }
];
