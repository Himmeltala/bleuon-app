<script setup lang="ts">
import Login from "./components/Login.vue";
import Logon from "./components/Logon.vue";

const tabs = {
  Login,
  Logon
};
const dynamicCompName = ref<"Login" | "Logon">("Login");
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
