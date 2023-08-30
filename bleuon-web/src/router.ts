import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      name: "public-welcome",
      meta: { title: "欢迎" },
      path: "/",
      component: () => import("@/views/Welcome.vue")
    },
    {
      name: "auth-home",
      meta: { title: "首页" },
      path: "/home",
      redirect: "/home/history",
      component: () => import("@/views/home/Home.vue"),
      children: [
        {
          name: "auth-history",
          meta: { title: "最近文件" },
          path: "history",
          component: () => import("@/views/home/History.vue")
        },
        {
          name: "auth-diagrams",
          meta: { title: "我的文件" },
          path: "diagrams",
          component: () => import("@/views/home/Diagrams.vue")
        },
        {
          name: "auth-shares",
          meta: { title: "我的分享" },
          path: "shares",
          component: () => import("@/views/home/Shares.vue")
        },
        {
          name: "auth-stars",
          meta: { title: "我的收藏" },
          path: "stars",
          component: () => import("@/views/home/Stars.vue")
        }
      ]
    },
    {
      name: "enter-entrance",
      meta: { title: "入口" },
      path: "/entrance",
      component: () => import("@/views/entrance/Entrance.vue")
    },
    {
      path: "/u",
      children: [
        {
          name: "public-profile",
          meta: { title: "个人空间" },
          path: "profile/:uid",
          component: () => import("@/views/user/Profile.vue")
        },
        {
          name: "auth-setting",
          meta: { title: "个人设置" },
          path: "setting",
          component: () => import("@/views/user/Setting.vue")
        }
      ]
    },
    {
      path: "/diagraming/:id",
      name: "auth-diagraming",
      meta: { title: "画图" },
      component: () => import("@/views/diagraming/Diagraming.vue")
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
  return !!localStorage.getStorageWithAge("BleuOn-Token");
}

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = ("BleuOn - " + to.meta.title) as string;
  }

  const toName = to.name.toString();
  const isAuth = isAuthenticated();

  if (toName.startsWith("auth-") && !isAuth) {
    next("/entrance");
    ElMessage.error("您未进行登录！已导航至登录页");
  } else if (toName.startsWith("public-")) {
    next();
  } else if (toName.startsWith("enter-") && isAuth) {
    next("/home");
    ElMessage.warning("您已经登陆！已导航至首页");
  } else {
    next();
  }
});

export default router;
