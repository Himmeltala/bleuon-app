/**
 * @description 配置 axios
 * @author zheng
 * @since 2023/9/9
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { InterceptorUtil } from "@common/utils";
import axios, { AxiosResponse, InternalAxiosRequestConfig } from "axios";

const http = axios.create({
  baseURL: `http://localhost:8080/api`
});

export function createRequest(name: "mainapp" | "subapp") {
  const tokenKey = name === "mainapp" ? KeyVals.MAINAPP_TOKEN_KEY : KeyVals.SUBAPP_TOKEN_KEY;

  const requestInterceptor = (request: InternalAxiosRequestConfig) => {
    const token = localStorage.getToken(tokenKey);

    if (token) {
      request.headers.Authorization = `Bearer ${token.value}`;
    }

    return request;
  };

  const responseInterceptor = (response: AxiosResponse) => {
    const { data, config } = response;

    if (config.ignoreError) {
      data.message && ElMessage.info(data.message);
      return response;
    }

    if (data.code == 500) {
      data.message && ElMessage.error(data.message);
      return Promise.reject(response);
    }

    if (data.code == 403) {
      data.message && ElMessage.warning(data.message);
      location.reload();
      localStorage.removeItem(tokenKey);
    } else if (data.code == 400) {
      data.message && ElMessage.warning(data.message);
    }

    if (
      !config.ignoreMsg &&
      data.code == 200 &&
      !InterceptorUtil.notInterceptUrl(response.config, { fuzzy: ["find", "replicate"] })
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
