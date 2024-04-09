<script lang="ts" setup>
/**
 * @description App.vue 程序入口组件
 * @author zheng
 * @since 2023/6/23
 */

const html = document.querySelector("html");
const themeMode = useStorage(Consts.MAINAPP_THEME_MODE, "");

onBeforeMount(() => {
  const name = themeMode.value === "dark" ? "dark" : "light";
  html.className = name;
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
