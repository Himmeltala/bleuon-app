<script lang="ts" setup>
/**
 * @description App.vue 程序入口组件
 * @author zheng
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

const root = document.querySelector("html");
const mode = useStorage(KeyVals.MAINAPP_THEME_MODE, "");

onBeforeMount(() => {
  const modeName = mode.value === "dark" ? "dark" : "light";
  root.className = modeName;
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
