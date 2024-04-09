<script lang="ts" setup>
/**
 * @description 登录入口路由组件
 * @author zheng
 * @since 2023/8/23
 */

import Login from "./Login.vue";
import Register from "./Register.vue";
import FindAccount from "./FindAccount.vue";

const tabs = {
  Login,
  Register,
  FindAccount
};
const dynamicCompName = ref<"Login" | "Register" | "FindAccount">("Login");
</script>

<template>
  <div class="entrance f-c-c h-100vh">
    <div class="w-40% h-100% relative">
      <img class="w-45 h-15 object-cover absolute top-10 left-10" src="/bleuon-icon.png" />
      <div class="absolute top-50% left-30 color-gray-100 text-2rem">
        在同一个地方，构思、设计<br />
        ，让你更加高效<br />
        <span class="text-1rem">√永久免费 √实时在线 √设计/原型/开发</span>
      </div>
      <div class="absolute bottom-10 left-10 color-gray-200 text-0.9rem">
        © 2023 郑人滏. All rights reserved.
      </div>
      <img
        class="w-100% h-100% object-fill"
        src="https://img.js.design/assets/Resources/background/login-bg-5.jpg" />
    </div>
    <div class="w-60% h-100%">
      <component
        :is="tabs[dynamicCompName]"
        v-model:dynamic-comp-name="dynamicCompName"></component>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
