/**
 * @description 配置路由组件
 * @author zheng
 * @since 2023/10/22
 * @link https://gitee.com/himmelbleu/bleuon-app
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
      redirect: "/home/consumer/find",
      component: () => import("@subapp/views/BaseHome.vue"),
      children: [
        {
          path: "consumer",
          name: "auth-consumer-base",
          meta: { title: "用户管理", icon: "User" },
          children: [
            {
              path: "find",
              name: "auth-consumer-find",
              meta: { title: "查询用户", icon: "Search" },
              component: () => import("@subapp/views/consumer/FindConsumer.vue")
            },
            {
              path: "add",
              name: "auth-consumer-add",
              meta: { title: "新增用户", icon: "Plus" },
              component: () => import("@subapp/views/consumer/AddConsumer.vue")
            },
            {
              path: "modify",
              name: "auth-consumer-modify",
              meta: { title: "修改用户", icon: "MagicStick" },
              component: () => import("@subapp/views/consumer/ModifyConsumer.vue")
            }
          ]
        },
        {
          path: "admin",
          name: "auth-admin-base",
          meta: { title: "管理员管理", icon: "UserFilled" },
          children: [
            {
              path: "find",
              name: "auth-admin-find",
              meta: { title: "查询管理员", icon: "Search" },
              component: () => import("@subapp/views/admin/FindAdmin.vue")
            },
            {
              path: "add",
              name: "auth-admin-add",
              meta: { title: "新增管理员", icon: "Plus" },
              component: () => import("@subapp/views/admin/AddAdmin.vue")
            },
            {
              path: "modify",
              name: "auth-admin-modify",
              meta: { title: "修改管理员", icon: "MagicStick" },
              component: () => import("@subapp/views/admin/ModifyAdmin.vue")
            }
          ]
        },
        {
          path: "authority",
          name: "auth-authority-base",
          meta: { title: "管理员管理", icon: "Lock" },
          children: [
            {
              path: "consumer",
              name: "auth-authority-consumer",
              meta: { title: "用户权限", icon: "User" },
              component: () => import("@subapp/views/authority/ConsumerAuthority.vue")
            },
            {
              path: "admin",
              name: "auth-authority-admin",
              meta: { title: "管理员权限", icon: "UserFilled" },
              component: () => import("@subapp/views/authority/AdminAuthority.vue")
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

  if (to.matched.length === 0) {
    next(false);
  } else {
    if (name.startsWith("auth-") && !isAuth) {
      next("/login");
    } else if (name.startsWith("enter-") && isAuth) {
      next("/");
    } else {
      next();
    }
  }
});

export default router;
