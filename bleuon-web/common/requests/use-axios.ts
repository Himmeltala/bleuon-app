/**
 * @description 配置 axios
 * @author zheng
 * @since 2023/9/9
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { InterceptorUtil } from "@common/utils";
import axios from "axios";

const http = axios.create({
  baseURL: `http://localhost:8080/api`
});

export function createRequest(name: "mainapp" | "subapp") {
  if (name === "mainapp") {
    http.interceptors.request.use(
      config => {
        const token = localStorage.getToken(KeyVals.MAINAPP_TOKEN_KEY);

        if (token) {
          config.headers.Authorization = `Bearer ${token.value}`;
        }

        return config;
      },
      error => {
        ElMessage.error("啊哦，发生了错误！");
        return Promise.reject(error);
      }
    );

    http.interceptors.response.use(
      config => {
        const { data, config: configuration } = config;

        if (data.code == 500) {
          data.message && ElMessage.error(data.message);
          return Promise.reject(config);
        } else if (data.code == 403) {
          data.message && ElMessage.warning(data.message);
          location.reload();
          localStorage.removeItem(KeyVals.MAINAPP_TOKEN_KEY);
        } else if (data.code == 400) {
          data.message && ElMessage.warning(data.message);
        }

        if (!configuration.nomessage) {
          if (
            data.code == 200 &&
            !InterceptorUtil.notInterceptUrl(config.config, {
              fuzzy: ["find", "replicate"]
            })
          ) {
            data.message && ElMessage.success(data.message);
          }
        }

        return config;
      },
      error => {
        ElMessage.error("啊哦，发生了错误！");
        return Promise.reject(error);
      }
    );
  } else if (name === "subapp") {
    http.interceptors.request.use(
      config => {
        const token = localStorage.getToken(KeyVals.SUBAPP_TOKEN_KEY);

        if (token) {
          config.headers.Authorization = `Bearer ${token.value}`;
        }

        return config;
      },
      error => {
        ElMessage.error("啊哦，发生了错误！");
        return Promise.reject(error);
      }
    );

    http.interceptors.response.use(
      config => {
        const { data, config: configuration } = config;

        if (data.code == 500) {
          data.message && ElMessage.error(data.message);
          return Promise.reject(config);
        } else if (data.code == 403) {
          data.message && ElMessage.warning(data.message);
          location.reload();
          localStorage.removeItem(KeyVals.SUBAPP_TOKEN_KEY);
        } else if (data.code == 400) {
          data.message && ElMessage.warning(data.message);
        }

        if (!configuration.nomessage) {
          if (
            data.code == 200 &&
            !InterceptorUtil.notInterceptUrl(config.config, {
              fuzzy: ["find", "replicate"]
            })
          ) {
            data.message && ElMessage.success(data.message);
          }
        }

        return config;
      },
      error => {
        ElMessage.error("啊哦，发生了错误！");
        return Promise.reject(error);
      }
    );
  }
}

export { http };
