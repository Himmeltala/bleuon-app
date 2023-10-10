/**
 * @description 配置路由组件
 * @author zheng
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
      path: "/community",
      children: [
        {
          path: "template",
          name: "auth-template-community",
          meta: { title: "模板社区" },
          component: () => import("@mainapp/views/community/TemplateCommunity.vue")
        },
        {
          path: "discussion",
          name: "auth-discussion-community",
          meta: { title: "讨论社区" },
          component: () => import("@mainapp/views/community/DiscussionCommunity.vue")
        }
      ]
    },
    {
      path: "/template",
      children: [
        {
          path: "flowchart/:id",
          name: "auth-template-flowchart",
          meta: { title: "流程图模板" },
          component: () => import("@mainapp/views/community/template/FlowchartTemplateDetail.vue")
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
          name: "public-share-flowchart",
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
  return !!localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);
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
    next("/workbench");
  } else {
    next();
  }
});

export default router;
