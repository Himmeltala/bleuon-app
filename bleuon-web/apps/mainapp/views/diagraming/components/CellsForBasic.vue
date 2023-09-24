<script setup lang="ts">
/**
 * @description 基础图形创建组件
 * @author 郑人滏 42020306
 * @since 2023/9/25
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";

defineProps({
  paper: {
    type: dia.Paper
  },
  graph: {
    type: dia.Graph
  }
});
</script>

<template>
  <div class="cells-for-flowchart">
    <div class="mb-4 font-bold">基础图形</div>
    <div class="f-c-s flex-wrap flex-gap-5"></div>
  </div>
</template>

<style scoped lang="scss"></style>
