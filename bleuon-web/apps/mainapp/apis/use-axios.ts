/**
 * @description 配置 axios
 * @author zheng
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { InterceptorUtil } from "@common/utils";
import axios from "axios";

const request = axios.create({
  baseURL: `http://localhost:8080/api`
});

request.interceptors.request.use(
  config => {
    const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);

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

request.interceptors.response.use(
  config => {
    const { data, config: configuration } = config;

    if (data.code == 500) {
      data.message && ElMessage.error(data.message);
      return Promise.reject(config);
    } else if (data.code == 403) {
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

export default request;
