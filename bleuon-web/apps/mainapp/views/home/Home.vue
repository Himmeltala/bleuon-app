<script setup lang="ts">
/**
 * @description 工作台页
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import type { ActiveItem } from "@mainapp/typings/home";
import Menu from "@mainapp/components/home/Menu.vue";

const activeItem = ref<ActiveItem>("auth-flowcharts");
</script>

<template>
  <div class="home f-s-c bg-#F6F7F8">
    <Menu class="w-17%" v-model:active-item="activeItem"></Menu>
    <div class="content h-100vh flow-auto px-10 pb-10 w-83%">
      <RouterView></RouterView>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
