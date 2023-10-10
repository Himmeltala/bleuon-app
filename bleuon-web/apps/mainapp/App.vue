<script lang="ts" setup>
/**
 * @description App.vue 程序入口组件
 * @author zheng
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";

const html = document.querySelector("html");
const themeMode = useStorage(KeyVals.MAINAPP_THEME_MODE, "");
const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);
const user = useStorage<UserData>(KeyVals.MAINAPP_USER, {});

onBeforeMount(() => {
  const name = themeMode.value === "dark" ? "dark" : "light";
  html.className = name;
});

onMounted(() => {
  if (token) {
    UserApi.find().then(res => {
      user.value = res;
    });
  }
});
</script>

<template>
  <RouterView v-slot="{ Component }">
    <template v-if="Component">
      <Suspense>
        <component :is="Component" />
      </Suspense>
    </template>
  </RouterView>
</template>

<style lang="scss" scoped></style>
