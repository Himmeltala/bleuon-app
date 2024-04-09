/**
 * @description 配置路由组件
 * @author zheng
 * @since 2023/6/23
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
      path: "/workbench",
      redirect: "/workbench/flowchart-list",
      component: () => import("@mainapp/views/workbench/Workbench.vue"),
      children: [
        {
          path: "flowchart-list",
          name: "auth-flowchart-list",
          meta: { title: "我的流程图" },
          component: () => import("@mainapp/views/workbench/FlowchartList.vue")
        },
        {
          path: "flowchart-stars",
          name: "auth-flowchart-stars",
          meta: { title: "我收藏的流程图" },
          component: () => import("@mainapp/views/workbench/FlowchartStars.vue")
        },
        {
          path: "canvas-list",
          name: "auth-canvas-list",
          meta: { title: "我的画布" },
          component: () => import("@mainapp/views/workbench/CanvasList.vue")
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
          path: "profile/:id",
          name: "auth-profile",
          meta: { title: "个人空间" },
          component: () => import("@mainapp/views/consumer/Profile.vue")
        },
        {
          path: "setting",
          name: "auth-setting",
          meta: { title: "个人设置" },
          component: () => import("@mainapp/views/consumer/Setting.vue")
        }
      ]
    },
    {
      path: "/community",
      children: [
        {
          path: "blueprint",
          name: "auth-blueprint-square",
          meta: { title: "模板广场" },
          component: () => import("@mainapp/views/community/BlueprintSquare.vue")
        },
        {
          path: "discussion",
          name: "auth-discussion-square",
          meta: { title: "讨论广场" },
          component: () => import("@mainapp/views/community/DiscussionSquare.vue")
        },
        {
          path: "article/:id",
          name: "auth-article-detail",
          meta: { title: "帖子详情" },
          component: () => import("@mainapp/views/community/ArticleDetail.vue")
        },
        {
          path: "article-editor",
          name: "auth-article-editor",
          meta: { title: "帖子编辑器" },
          component: () => import("@mainapp/views/community/ArticleEditor.vue")
        }
      ]
    },
    {
      path: "/blueprint",
      children: [
        {
          path: "flowchart/:id",
          name: "auth-blueprint-flowchart",
          meta: { title: "流程图模板" },
          component: () => import("@mainapp/views/community/BlueprintFlowchartDetail.vue")
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
      path: "/share",
      children: [
        {
          path: "flowchart/:id",
          name: "auth-share-flowchart",
          meta: { title: "分享的流程图" },
          component: () => import("@mainapp/views/share/ShareFlowchart.vue")
        }
      ]
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
  return !!localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
}

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = ("BleuOn - " + to.meta.title) as string;
  }

  const name = to.name as string;
  const isAuth = isAuthenticated();

  if (to.matched.length === 0) {
    next(false);
  } else {
    if (name.startsWith("auth-") && !isAuth) {
      next("/entrance");
    } else if (name.startsWith("enter-") && isAuth) {
      next("/workbench");
    } else {
      next();
    }
  }
});

export default router;
