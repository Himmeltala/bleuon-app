<script setup lang="ts">
/**
 * @description 登录入口路由组件
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
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
    <div class="w-30% h-100% bg-#f0f0f0"></div>
    <div class="w-70% h-100%">
      <component
        v-model:dynamic-comp-name="dynamicCompName"
        :is="tabs[dynamicCompName]"></component>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
