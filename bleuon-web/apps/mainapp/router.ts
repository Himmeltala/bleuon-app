/**
 * @description 配置路由组件
 * @author 郑人滏 42020306
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      path: "/",
      name: "public-welcome",
      meta: { title: "欢迎" },
      component: () => import("@mainapp/views/Welcome.vue")
    },
    {
      path: "/home",
      name: "auth-home",
      meta: { title: "首页" },
      redirect: "/home/flowcharts",
      component: () => import("@mainapp/views/home/Home.vue"),
      children: [
        {
          path: "flowcharts",
          name: "auth-flowcharts",
          meta: { title: "我的流程图" },
          component: () => import("@mainapp/views/home/Flowcharts.vue")
        },
        {
          path: "diagrams",
          name: "auth-diagrams",
          meta: { title: "我的文件" },
          component: () => import("@mainapp/views/home/Diagrams.vue")
        },
        {
          path: "shares",
          name: "auth-shares",
          meta: { title: "我的分享" },
          component: () => import("@mainapp/views/home/Shares.vue")
        },
        {
          path: "stars",
          name: "auth-stars",
          meta: { title: "我的收藏" },
          component: () => import("@mainapp/views/home/Stars.vue")
        }
      ]
    },
    {
      path: "/entrance",
      name: "enter-entrance",
      meta: { title: "入口" },
      component: () => import("@mainapp/views/entrance/Entrance.vue")
    },
    {
      path: "/u",
      children: [
        {
          path: "profile/:uid",
          name: "public-profile",
          meta: { title: "个人空间" },
          component: () => import("@mainapp/views/user/Profile.vue")
        },
        {
          path: "setting",
          name: "auth-setting",
          meta: { title: "个人设置" },
          component: () => import("@mainapp/views/user/Setting.vue")
        }
      ]
    },
    {
      path: "/flowchart/:id",
      name: "auth-flowchat",
      meta: { title: "流程图" },
      component: () => import("@mainapp/views/diagraming/Flowchart.vue")
    },
    {
      path: "/canvas/:id",
      name: "auth-canvas",
      meta: { title: "画布" },
      component: () => import("@mainapp/views/diagraming/Canvas.vue")
    },
    {
      path: "/mind/:id",
      name: "auth-mindmapping",
      meta: { title: "思维导图" },
      component: () => import("@mainapp/views/diagraming/MindMapping.vue")
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
    ElMessage.error("您未登录！已导航至登录页");
  } else if (name.startsWith("public-")) {
    next();
  } else if (name.startsWith("enter-") && isAuth) {
    next("/home");
    ElMessage.warning("您已登陆！已导航至首页");
  } else {
    next();
  }
});

export default router;
