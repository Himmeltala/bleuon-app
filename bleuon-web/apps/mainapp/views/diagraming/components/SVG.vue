<script setup lang="ts">
/**
 * @description Path SVG 创建组件
 * @author 郑人滏 42020306
 * @since 2023/9/26
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, Path } from "@mainapp/lib";

defineProps({
  graph: {
    type: dia.Graph
  },
  viewBox: {
    type: String,
    required: true
  },
  d: {
    type: String,
    required: true
  },
  content: {
    type: String,
    required: true
  },
  strokeWidth: {
    type: String,
    default: "0.3"
  },
  stroke: {
    type: String,
    default: "#000000"
  },
  width: {
    type: Number,
    default: 140
  },
  height: {
    type: Number,
    default: 70
  },
  flex: {
    type: String,
    default: "0 1 15%"
  }
});
</script>

<template>
  <el-tooltip :content="content">
    <svg
      :style="{ flex: flex }"
      @click="Path.create(graph, { d, width, height })"
      xmlns="http://www.w3.org/2000/svg"
      :viewBox="viewBox">
      <path :d="d" :stroke="stroke" :stroke-width="strokeWidth" fill="#ffffff" />
    </svg>
  </el-tooltip>
</template>

<style scoped lang="scss">
svg {
  --uno: w-10 h-10 object-cover cursor-pointer;
}
</style>
