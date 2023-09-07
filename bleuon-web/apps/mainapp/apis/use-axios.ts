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

    if (data.code === "SUCCESS") {
      ElMessage.success(data.message);
      return config;
    }

    if (data.code === "NO_AUTHORITY") {
      localStorage.removeItem("BleuOn-Token");
      setTimeout(location.reload, 500);
    }

    ElMessage.error(data.message);
    return Promise.reject(data.message);
  },
  error => {
    ElMessage.error("发生错误！");
    return Promise.reject(error);
  }
);

export default request;
