/**
 * @description 配置路由组件
 * @author zheng
 * @since 2023/10/22
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      path: "/",
      redirect: "/manager/consumer",
      children: [
        {
          path: "manager/consumer",
          name: "auth-consumer-manager",
          meta: { title: "用户管理" },
          component: () => import("@subapp/views/ConsumerManager.vue")
        }
      ]
    },
    {
      path: "/login",
      name: "enter-login",
      meta: { title: "登录" },
      component: () => import("@subapp/views/Login.vue")
    }
  ],
  history: createWebHashHistory(),
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { el: "#app", top: 0, behavior: "smooth" };
    }
  }
});

function isAuthenticated() {
  return !!localStorage.getToken(KeyVals.SUBAPP_TOKEN_KEY);
}

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = "BleuOn - " + to.meta.title.toString();
  }

  const name = to.name.toString();
  const isAuth = isAuthenticated();

  if (name.startsWith("auth-") && !isAuth) {
    next("/login");
  } else if (name.startsWith("enter-") && isAuth) {
    next("/");
  } else {
    next();
  }
});

export default router;
