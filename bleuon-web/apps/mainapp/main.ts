/**
 * @description 程序入口函数
 * @author zheng
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

/* Import Vue */
import App from "@mainapp/App.vue";
import router from "@mainapp/router";
import { createPinia } from "pinia";
import { createApp } from "vue";
/* Import CSS */
import "@mainapp/style.scss";
import "@common/utils/local-storage";
import "uno.css";

const app = createApp(App);
app.use(router);
app.use(createPinia());

app.mount("#app");
