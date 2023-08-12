import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  routes: [
    {
      name: "Welcome",
      path: "/",
      component: () => import("@/views/Welcome.vue")
    },
    {
      name: "Login",
      path: "/login",
      component: () => import("@/views/login/Login.vue")
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
