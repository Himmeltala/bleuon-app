<script setup lang="ts">
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
      <KeepAlive>
        <Suspense>
          <component :is="Component" />
        </Suspense>
      </KeepAlive>
    </template>
  </RouterView>
</template>

<style scoped lang="scss"></style>
