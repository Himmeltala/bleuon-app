<script lang="ts" setup>
/**
 * @description App.vue 程序入口组件
 * @author 郑人滏 42020306
 * @since 2023/6/23
 * @link https://github.com/himmelbleu/bleuon-app
 */
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
