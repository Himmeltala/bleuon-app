import axios from "axios";

const request = axios.create({
  baseURL: `localhost:8080`,
  timeout: 5000
});

request.interceptors.request.use(
  config => {
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

export default request;
