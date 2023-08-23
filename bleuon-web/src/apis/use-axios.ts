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
    return Promise.reject(error);
  }
);

request.interceptors.response.use(
  config => {
    const { data } = config;

    // 权限相关的错误请求
    if (data.body.code > 40000) {
      ElMessage.error(data.body.message);
      localStorage.removeItem("BleuOn-Token");
      location.reload();
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

export default request;
