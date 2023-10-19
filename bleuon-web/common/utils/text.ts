/**
 * 统计纯文本中的中英文等字符的长度
 *
 * @param str
 * @returns
 */
export function strlen(str: string) {
  // 获取字符串的字节数
  let count = 0; //初始化字节数递加变量并获取字符串参数的字符个数
  // 如果存在字符串，则执行
  let withoutTags = str.replace(/<\/?[^>]+(>|$)/g, ""); // 移除HTML标签
  let len = withoutTags.length;
  for (let i = 0; i < len; i++) {
    // 遍历字符串，枚举每个字符
    if (str.charCodeAt(i) > 255) {
      // 字符编码大于255，说明是双字节字符(即是中文)
      count += 2; // 则累加2个
    } else {
      count++; // 否则递加一次
    }
  }
  return count; // 返回字节数
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
