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
    ElMessage.error("啊哦，发生了错误！");
    return Promise.reject(error);
  }
);

request.interceptors.response.use(
  config => {
    const { data } = config;

    if (data.code === 200) {
      ElMessage.success(data.message);
      return config;
    }

    if (data.code === 400) {
      ElMessage.warning(data.message);
      return config;
    }

    if (data.code === 403) {
      localStorage.removeItem("BleuOn-Token");
      setTimeout(location.reload, 500);
      return config;
    }

    if (data.code === 500) {
      ElMessage.error(data.message);
      return Promise.reject(data.message);
    }
  },
  error => {
    ElMessage.error("啊哦，发生了错误！");
    return Promise.reject(error);
  }
);

export default request;
