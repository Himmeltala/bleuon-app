<script lang="ts" setup>
/**
 * @description Flowchart 侧边栏工具
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import CellsForFlowchart from "./CellsForFlowchart.vue";
import CellsForBasic from "./CellsForBasic.vue";
</script>

<template>
  <div
    class="bleuon__flowchart-sidebar slim-slider h-100vh flow-auto bg-bg-primary b-border-primary b-r-1 b-r-solid relative px-4 py-4">
    <CellsForBasic class="mb-4" />
    <CellsForFlowchart class="bg-bg-primary border-#dfe2e5 border-t-1 border-t-solid pt-4" />
  </div>
</template>

<style lang="scss" scoped></style>
