/**
 * @description 日期工具
 * @author zheng
 * @since 2023/9/29
 */

export function formatted(
  timestamp?: string,
  format?:
    | "yyyy-MM-dd HH:mm:ss"
    | "HH:mm:ss"
    | "yyyy-MM-dd"
    | "MM-dd HH:mm:ss"
    | "MM-dd"
    | "MM-dd HH:mm"
): string {
  let date = new Date(),
    pattern = `${format || "yyyy-MM-dd HH:mm:ss"}`;
  if (timestamp) {
    date = new Date(timestamp);
  }

  const year = String(date.getFullYear());
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");

  pattern = pattern.replace("yyyy", year);
  pattern = pattern.replace("MM", month);
  pattern = pattern.replace("dd", day);
  pattern = pattern.replace("HH", hours);
  pattern = pattern.replace("mm", minutes);
  pattern = pattern.replace("ss", seconds);

  return pattern;
}
