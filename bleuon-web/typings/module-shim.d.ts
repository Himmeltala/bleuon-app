import { AxiosInstance } from "axios";

declare module "vue" {
  interface ComponentCustomProperties {}
}

declare module "axios" {
  interface AxiosInstance {
    cancelSource: any;
    isCancel: any;
  }
}
