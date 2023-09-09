import axios from "axios";
import { notInterceptUrl } from "@common/utils/interceptor";

const request = axios.create({
  baseURL: `http://localhost:8080/api`
});

request.interceptors.request.use(
  config => {
    const token = localStorage.getStorageWithAge("BleuOn-Token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
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
    const { data } = config;

    if (data.code === 500) {
      ElMessage.error(data.message);
      return Promise.reject(data.message);
    } else if (data.code === 400) {
      ElMessage.warning(data.message);
    } else if (data.code === 403) {
      localStorage.removeItem("BleuOn-Token");
      setTimeout(location.reload, 500);
    }

    if (data.code === 200 && !notInterceptUrl(data, { fuzzy: ["query"] })) {
      ElMessage.success(data.message);
    }

    return config;
  },
  error => {
    ElMessage.error("啊哦，发生了错误！");
    return Promise.reject(error);
  }
);

export default request;
