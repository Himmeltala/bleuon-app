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
      redirect: "/home"
    },
    {
      path: "/home",
      name: "auth-base-home",
      meta: { title: "" },
      component: () => import("@subapp/views/BaseHome.vue"),
      redirect: "/home/consumer/find",
      children: [
        {
          path: "consumer",
          name: "auth-consumer-base",
          meta: { title: "用户管理" },
          component: () => import("@subapp/views/ConsumerBase.vue"),
          children: [
            {
              path: "find",
              name: "auth-consumer-find",
              meta: { title: "查询用户" },
              component: () => import("@subapp/views/consumer/FindConsumer.vue")
            }
          ]
        },
        {
          path: "admin",
          name: "auth-admin-base",
          meta: { title: "管理员管理" },
          component: () => import("@subapp/views/AdminBase.vue"),
          children: [
            {
              path: "find",
              name: "auth-admin-find",
              meta: { title: "查询管理员" },
              component: () => import("@subapp/views/admin/FindAdmin.vue")
            }
          ]
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
    document.title = "BleuOn 后台管理系统 - " + to.meta.title.toString();
  }

  const name = String(to.name);
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
