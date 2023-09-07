import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      name: "public-welcome",
      meta: { title: "欢迎" },
      path: "/",
      component: () => import("@mainapp/views/Welcome.vue")
    },
    {
      name: "auth-home",
      meta: { title: "首页" },
      path: "/home",
      redirect: "/home/history",
      component: () => import("@mainapp/views/home/Home.vue"),
      children: [
        {
          name: "auth-history",
          meta: { title: "最近文件" },
          path: "history",
          component: () => import("@mainapp/views/home/History.vue")
        },
        {
          name: "auth-diagrams",
          meta: { title: "我的文件" },
          path: "diagrams",
          component: () => import("@mainapp/views/home/Diagrams.vue")
        },
        {
          name: "auth-shares",
          meta: { title: "我的分享" },
          path: "shares",
          component: () => import("@mainapp/views/home/Shares.vue")
        },
        {
          name: "auth-stars",
          meta: { title: "我的收藏" },
          path: "stars",
          component: () => import("@mainapp/views/home/Stars.vue")
        }
      ]
    },
    {
      name: "enter-entrance",
      meta: { title: "入口" },
      path: "/entrance",
      component: () => import("@mainapp/views/entrance/Entrance.vue")
    },
    {
      path: "/u",
      children: [
        {
          name: "public-profile",
          meta: { title: "个人空间" },
          path: "profile/:uid",
          component: () => import("@mainapp/views/user/Profile.vue")
        },
        {
          name: "auth-setting",
          meta: { title: "个人设置" },
          path: "setting",
          component: () => import("@mainapp/views/user/Setting.vue")
        }
      ]
    },
    {
      path: "/diagraming/:id",
      name: "auth-diagraming",
      meta: { title: "画图" },
      component: () => import("@mainapp/views/diagraming/Diagraming.vue")
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
  return !!localStorage.getStorageWithAge(KeyVals.MAINAPP_TOKEN_KEY);
}

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = ("BleuOn - " + to.meta.title) as string;
  }

  const name = to.name as string;
  const isAuth = isAuthenticated();

  if (name.startsWith("auth-") && !isAuth) {
    next("/entrance");
    ElMessage.error("您未进行登录！已导航至登录页");
  } else if (name.startsWith("public-")) {
    next();
  } else if (name.startsWith("enter-") && isAuth) {
    next("/home");
    ElMessage.warning("您已经登陆！已导航至首页");
  } else {
    next();
  }
});

export default router;
