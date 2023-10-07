/**
 * @description 程序入口函数
 * @author zheng
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

/* Import Vue */
import App from "@mainapp/App.vue";
import router from "@mainapp/router";
import { createApp } from "vue";
import { createPinia } from "pinia";
/* Import CSS */
import "uno.css";
import "@mainapp/style.scss";
import "@mainapp/utils/local-storage";

const app = createApp(App);
app.use(router);
app.use(createPinia());

app.mount("#app");
