import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      name: "Home",
      path: "/",
      component: () => import("@/views/Home.vue")
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

export default router;
