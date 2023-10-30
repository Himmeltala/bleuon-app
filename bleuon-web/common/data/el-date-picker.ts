/**
 * @description element-plus el-date-picker 通用数据
 * @author zheng
 * @since 2023/10/6
 */

/**
 * 从明天开始的未来日期快捷选择
 *
 * 包括：明天、一周后、两周后、一月后、两月后
 */
export const futureShortcuts = [
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
