/**
 * @description 日期工具
 * @author 郑人滏 42020306
 * @since 2023/9/29
 * @link https://github.com/himmelbleu/bleuon-app
 */

export function formatted(format: string, date?: Date): string {
  if (!date) {
    date = new Date();
  }
  const year = String(date.getFullYear());
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");

  format = format.replace("yyyy", year);
  format = format.replace("MM", month);
  format = format.replace("dd", day);
  format = format.replace("HH", hours);
  format = format.replace("mm", minutes);
  format = format.replace("ss", seconds);

  return format;
}
