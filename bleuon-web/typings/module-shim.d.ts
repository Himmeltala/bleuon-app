import axios, { AxiosRequestConfig } from "axios";

declare module "vue" {
  interface ComponentCustomProperties {}
}

declare module "axios" {
  interface AxiosRequestConfig {
    /**
     * 是否关闭消息提示
     */
    nomessage?: boolean;
  }
}
