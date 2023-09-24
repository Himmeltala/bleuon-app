<script setup lang="ts">
/**
 * @description Flowchat 侧边栏工具
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import { CreatorService } from "../service";

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
  <div
    class="bleuon__flowchat-sidebar bg-#f6f7f8 border-#dfe2e5 border-r-1 border-r-solid relative px-4 py-4">
    <el-button @click="CreatorService.addPrimaryRectangle(graph)">矩形</el-button>
    <el-button @click="CreatorService.addPrimaryCircle(graph)">圆形</el-button>
  </div>
</template>

<style scoped lang="scss"></style>
