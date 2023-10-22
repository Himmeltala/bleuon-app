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
      name: "auth-home",
      meta: { title: "" },
      redirect: "/home/manage/consumer",
      component: () => import("@subapp/views/BaseHome.vue"),
      children: [
        {
          path: "manage/consumer",
          name: "auth-manage-consumer",
          meta: { title: "用户管理" },
          component: () => import("@subapp/views/ManageConsumer.vue")
        },
        {
          path: "manage/admins",
          name: "auth-manage-admins",
          meta: { title: "管理员管理" },
          component: () => import("@subapp/views/ManageAdmins.vue")
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
