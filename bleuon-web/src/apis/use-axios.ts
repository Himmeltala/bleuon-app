import axios from "axios";

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
    ElMessage.error("发生错误！");
    return Promise.reject(error);
  }
);

request.interceptors.response.use(
  config => {
    const { data } = config;

    if (data.code >= 40100 && data.code <= 40199) {
      ElMessage.error("权限错误：" + data.message);
      localStorage.removeItem("BleuOn-Token");

      setTimeout(() => {
        location.reload();
      }, 500);

      return Promise.reject(data.message);
    } else if (data.code > 40199 && data.code <= 40299) {
      ElMessage.error("错误：" + data.message);
      return Promise.reject(data.message);
    } else if (data.code >= 50000 && data.code < 60000) {
      ElMessage.error("错误：" + data.message);
    } else {
      ElMessage.success(data.message);
    }

    return config;
  },
  error => {
    ElMessage.error("发生错误！");
    return Promise.reject(error);
  }
);

export default request;
