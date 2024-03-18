/**
 * @description 配置路由组件
 * @author zheng
 * @since 2023/10/22
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
      redirect: "/home/sysrc/grant-admin-roles",
      component: () => import("@subapp/views/BaseHome.vue"),
      children: [
        {
          path: "peoples",
          meta: { title: "人员管理", icon: "Collection" },
          children: [
            {
              path: "consumers",
              name: "auth-consumers",
              meta: { title: "用户", icon: "User" },
              component: () => import("@subapp/views/pepoles/Consumers.vue")
            },
            {
              path: "admins",
              name: "auth-admins",
              meta: { title: "管理员", icon: "UserFilled" },
              component: () => import("@subapp/views/pepoles/Admins.vue")
            }
          ]
        },
        {
          path: "sysrc",
          meta: { title: "系统资源", icon: "Loading" },
          children: [
            {
              path: "maintain-admin-roles",
              name: "auth-maintain-admin-roles",
              meta: { title: "维护管理员角色", icon: "UserFilled" },
              component: () => import("@subapp/views/sysrc/MaintainAdminRoles.vue")
            },
            {
              path: "grant-admin-roles",
              name: "auth-grant-admin-roles",
              meta: { title: "分配管理员角色", icon: "Files" },
              component: () => import("@subapp/views/sysrc/GrantAdminRoles.vue")
            },
            {
              path: "authorities",
              name: "auth-authorities",
              meta: { title: "权限管理", icon: "Lock" },
              component: () => import("@subapp/views/sysrc/Authorities.vue")
            },
            {
              path: "roles-and-authorities",
              name: "auth-roles-and-authorities",
              meta: { title: "角色及权限", icon: "Key" },
              component: () => import("@subapp/views/sysrc/RolesAndAuthorities.vue")
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
  return !!localStorage.getToken(Consts.SUBAPP_TOKEN_KEY);
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
