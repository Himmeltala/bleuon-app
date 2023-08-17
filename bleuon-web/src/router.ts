import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      name: "Welcome",
      meta: { title: "欢迎" },
      path: "/",
      component: () => import("@/views/Welcome.vue")
    },
    {
      name: "Home",
      meta: { title: "首页" },
      path: "/home",
      component: () => import("@/views/home/Home.vue"),
      children: [
        {
          name: "MyRecent",
          meta: { title: "最近文件" },
          path: "myrecent",
          component: () => import("@/views/home/MyRecent.vue")
        },
        {
          name: "MyFiles",
          meta: { title: "我的文件" },
          path: "myfiles",
          component: () => import("@/views/home/MyFiles.vue")
        },
        {
          name: "MyShares",
          meta: { title: "我的分享" },
          path: "myshares",
          component: () => import("@/views/home/MyShares.vue")
        },
        {
          name: "MyStars",
          meta: { title: "我的收藏" },
          path: "mystars",
          component: () => import("@/views/home/MyStars.vue")
        }
      ]
    },
    {
      name: "Entrance",
      meta: { title: "入口" },
      path: "/entrance",
      component: () => import("@/views/entrance/Entrance.vue")
    },

    {
      path: "/u",
      children: [
        {
          name: "Profile",
          meta: { title: "个人空间" },
          path: "profile/:uid",
          component: () => import("@/views/user/Profile.vue")
        },
        {
          name: "Setting",
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

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = ("BleuOn - " + to.meta.title) as string;
  }
  next();
});

export default router;
