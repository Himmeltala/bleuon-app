/**
 * @description 程序入口函数
 * @author zheng
 * @since 2023/6/23
 */

/* Import Vue */
import { createPinia } from "pinia";
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
/* Import CSS */
import "uno.css";
import "@common/scss/global-style.scss";
import "@common/utils/local-storage";
/* Import Axios */
import { createRequest } from "@common/requests/use-axios";

createRequest("mainapp");

const app = createApp(App);
app.use(router);
app.use(createPinia());
app.mount("#app");
