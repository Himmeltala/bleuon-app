<script lang="ts" setup>
/**
 * @description Flowchart 侧边栏工具
 * @author zheng
 * @since 2023/9/24
 */

import CellsFlowchart from "./CellsFlowchart.vue";
import CellsBasic from "./CellsBasic.vue";
</script>

<template>
  <div
    class="bleuon__flowchart-sidebar slim-slider h-100vh flow-auto bg-bg-primary b-border-primary b-r-1 b-r-solid relative px-4 py-4">
    <CellsBasic class="mb-4" />
    <CellsFlowchart class="bg-bg-primary b-border-primary b-t-1 b-t-solid pt-4" />
  </div>
</template>

<style lang="scss" scoped></style>
