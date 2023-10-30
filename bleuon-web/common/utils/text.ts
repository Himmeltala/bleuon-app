/**
 * @description 文本工具
 * @author zheng
 * @since 2023/10/20
 */

/**
 * 统计纯文本中的中英文等字符的长度
 *
 * @param str
 * @returns
 */
export function strlen(str: string) {
  const cleanedStr = str.replace(/<[^>]*>/g, "").replace(/&[a-z]+;/g, "");
  let count = 0;
  for (let i = 0; i < cleanedStr.length; i++) {
    count++;
  }
  return count;
}

/**
 * 统计汉字字数
 *
 * @param str 字符串
 * @returns
 */
export function strlenForZh(str: string) {
  let total = 0;
  let withoutTags = str.replace(/<\/?[^>]+(>|$)/g, ""); // 移除HTML标签
  for (let i = 0; i < str.length; i++) {
    let c = withoutTags.charAt(i);
    // 基本汉字
    if (c.match(/[\u4e00-\u9fa5]/)) {
      total++;
    }
    // 基本汉字补充
    else if (c.match(/[\u9FA6-\u9fcb]/)) {
      total++;
    }
  }
  return total;
}
