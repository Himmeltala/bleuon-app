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
      redirect: "/home/myrecent",
      component: () => import("@/views/home/Home.vue"),
      children: [
        {
          name: "auth-my-recent",
          meta: { title: "最近文件" },
          path: "myrecent",
          component: () => import("@/views/home/MyRecent.vue")
        },
        {
          name: "auth-my-files",
          meta: { title: "我的文件" },
          path: "myfiles",
          component: () => import("@/views/home/MyFiles.vue")
        },
        {
          name: "auth-my-shares",
          meta: { title: "我的分享" },
          path: "myshares",
          component: () => import("@/views/home/MyShares.vue")
        },
        {
          name: "auth-my-stars",
          meta: { title: "我的收藏" },
          path: "mystars",
          component: () => import("@/views/home/MyStars.vue")
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
