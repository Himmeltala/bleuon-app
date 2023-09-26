<script setup lang="ts">
/**
 * @description Flowchart 侧边栏工具
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import CellsForFlowchart from "./CellsForFlowchart.vue";
import CellsForBasic from "./CellsForBasic.vue";

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
    class="bleuon__flowchart-sidebar slim-slider h-100vh flow-auto bg-#f6f7f8 border-#dfe2e5 border-r-1 border-r-solid relative px-4 py-4">
    <CellsForBasic class="mb-4" :paper="paper" :graph="graph" />
    <CellsForFlowchart
      class="bg-#f6f7f8 border-#dfe2e5 border-t-1 border-t-solid pt-4"
      :paper="paper"
      :graph="graph" />
  </div>
</template>

<style scoped lang="scss"></style>
