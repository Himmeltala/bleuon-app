<script setup lang="ts">
/**
 * @description App.vue 程序入口组件
 * @author zheng
 * @since 2023/10/22
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

<style scoped lang="scss"></style>
