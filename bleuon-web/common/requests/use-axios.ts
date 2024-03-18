/**
 * @description 配置 axios
 * @author zheng
 * @since 2023/9/9
 */

import { InterceptorUtil } from "@common/utils";
import axios, { AxiosResponse, InternalAxiosRequestConfig } from "axios";

const http = axios.create({
  baseURL: `http://localhost:8080/api`
});

export function createRequest(name: "mainapp" | "subapp") {
  const tokenKey = name === "mainapp" ? Consts.MAINAPP_TOKEN_KEY : Consts.SUBAPP_TOKEN_KEY;

  const requestInterceptor = (request: InternalAxiosRequestConfig) => {
    const token = localStorage.getToken(tokenKey);

    if (token) {
      request.headers.Authorization = `Bearer ${token.value}`;
    }

    return request;
  };

  const responseInterceptor = (response: AxiosResponse) => {
    const { data, config } = response;

    if (data.code === 500 && !config.ignore500) {
      data.message && ElMessage.error(data.message);
      return Promise.reject(response);
    } else if (data.code === 403) {
      data.message && ElMessage.warning(data.message);
      location.reload();
      localStorage.removeItem(tokenKey);
    } else if (data.code === 400) {
      data.message && ElMessage.info(data.message);
    }

    if (
      data.code === 200 &&
      !config.ignore200 &&
      !InterceptorUtil.matchURL(response.config, { pattern: ["find"] })
    ) {
      data.message && ElMessage.success(data.message);
    }

    return response;
  };

  http.interceptors.request.use(requestInterceptor, error => {
    ElMessage.error("啊哦，发生了错误！");
    return Promise.reject(error);
  });

  http.interceptors.response.use(responseInterceptor, error => {
    ElMessage.error("啊哦，发生了错误！");
    return Promise.reject(error);
  });
}

export { http };
