import axios, { AxiosRequestConfig } from "axios";

declare module "vue" {
  interface ComponentCustomProperties {}
}

declare module "axios" {
  interface AxiosRequestConfig {
    /**
     * 是否忽略 200 消息
     */
    ignore200?: boolean;
    /**
     * 是否忽略 500 消息
     */
    ignore500?: boolean;
  }
}
