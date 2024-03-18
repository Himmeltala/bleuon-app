/**
 * @description 防抖函数、节流函数等
 * @author 郑人滏 42020306
 * @since 2023/9/29
 * @link https://github.com/himmelbleu/bleuon-app
 */

export function debounce<T extends (...args: any[]) => void>(
  func: T,
  delay: number
): (...args: Parameters<T>) => void {
  let timer: number | undefined;

  return function (this: any, ...args: Parameters<T>): void {
    clearTimeout(timer);
    timer = setTimeout(() => {
      func.apply(this, args);
    }, delay);
  };
}

export function throttle<T extends (...args: any[]) => void>(
  func: T,
  delay: number
): (...args: Parameters<T>) => void {
  let lastExecTime = 0;

  return function (this: any, ...args: Parameters<T>): void {
    const now = Date.now();
    if (now - lastExecTime >= delay) {
      func.apply(this, args);
      lastExecTime = now;
    }
  };
}
