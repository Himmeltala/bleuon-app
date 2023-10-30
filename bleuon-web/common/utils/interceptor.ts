/**
 * @description axios 拦截器
 * @author zheng
 * @since 2023/9/17
 */

import type { InternalAxiosRequestConfig } from "axios";

/**
 * 在 axios 拦截器中做全局的消息提示或错误处理时，或者某些 URL 可能不需要携带 Token 时可用。
 *
 * @param axiosConfig axios 的请求配置
 * @param config 可以精准匹配 URL，也可以模糊匹配 URL 字符串
 * @returns 如果匹配到了就返回 true，如果没有匹配就返回 false
 */
export function matchURL(
  axiosConfig: InternalAxiosRequestConfig,
  config: {
    pattern: string[];
  }
): boolean {
  return config.pattern.some(i => {
    const regex = new RegExp(i);
    return regex.test(axiosConfig.url);
  });
}
