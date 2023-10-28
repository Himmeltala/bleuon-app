import axios, { AxiosRequestConfig } from "axios";

declare module "vue" {
  interface ComponentCustomProperties {}
}

declare module "axios" {
  interface AxiosRequestConfig {
    /**
     * 是否关闭消息提示
     */
    ignoreMsg?: boolean;
    /**
     * 不管是否 500 错误都不提示
     */
    ignoreError?: boolean;
  }
}
