<script lang="ts" setup>
/**
 * @description App.vue 程序入口组件
 * @author zheng
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";

const root = document.querySelector("html");
const mode = useStorage(KeyVals.MAINAPP_THEME_MODE, "");
const user = useStorage<UserData>(KeyVals.MAINAPP_USER, {});

onBeforeMount(() => {
  const modeName = mode.value === "dark" ? "dark" : "light";
  root.className = modeName;
});

onMounted(() => {
  UserApi.fineByToken().then(res => {
    user.value = res;
  });
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
