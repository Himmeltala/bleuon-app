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
      meta: { title: "首页", icon: "House" },
      redirect: "/home/permission/roles",
      component: () => import("@subapp/views/BaseHome.vue"),
      children: [
        {
          path: "consumer",
          meta: { title: "维护用户", icon: "User" },
          children: [
            {
              path: "find",
              name: "auth-find-consumers",
              meta: { title: "用户列表", icon: "Search" },
              component: () => import("@subapp/views/consumer/FindConsumers.vue")
            }
          ]
        },
        {
          path: "admin",
          meta: { title: "维护管理员", icon: "UserFilled" },
          children: [
            {
              path: "find",
              name: "auth-find-admins",
              meta: { title: "管理员列表", icon: "Search" },
              component: () => import("@subapp/views/admin/FindAdmins.vue")
            }
          ]
        },
        {
          path: "permission",
          meta: { title: "权限和角色", icon: "Key" },
          children: [
            {
              path: "consumer-roles",
              name: "auth-consumer-roles",
              meta: { title: "用户角色", icon: "User" },
              component: () => import("@subapp/views/permission/ConsumerRoles.vue")
            },
            {
              path: "admin-roles",
              name: "auth-admin-roles",
              meta: { title: "管理员角色", icon: "UserFilled" },
              component: () => import("@subapp/views/permission/AdminRoles.vue")
            },
            {
              path: "add-admin-role",
              name: "auth-add-admin-role",
              meta: { title: "分配管理员角色", icon: "FolderChecked" },
              component: () => import("@subapp/views/permission/AddAdminRole.vue")
            },
            {
              path: "authorities",
              name: "auth-authorities",
              meta: { title: "权限管理", icon: "Lock" },
              component: () => import("@subapp/views/permission/FindAuthorities.vue")
            },
            {
              path: "roles",
              name: "auth-roles",
              meta: { title: "角色分组", icon: "List" },
              component: () => import("@subapp/views/permission/FindRoles.vue")
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
    },
    {
      path: "/profile",
      name: "auth-profile",
      meta: { title: "个人资料" },
      component: () => import("@subapp/views/Profile.vue")
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
