<script lang="ts" setup>
/**
 * @description Path SVG 创建组件
 * @author zheng
 * @since 2023/9/26
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, Path } from "@mainapp/lib";

defineProps({
  viewBox: {
    type: String,
    required: true
  },
  content: {
    type: String,
    required: true
  },
  attrs: {
    type: Object,
    required: true
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

const graph = inject<dia.Graph>(KeyVals.BLEUON_FLOWCHART_GRAPH);
</script>

<template>
  <el-tooltip :content="content">
    <svg
      :style="{ flex: flex }"
      :viewBox="viewBox"
      xmlns="http://www.w3.org/2000/svg"
      @click="
        Path.create(graph, {
          width,
          height,
          attrs
        })
      ">
      <path
        v-for="(v, k) in attrs"
        :key="k"
        :d="v.refD"
        :fill="v.fill || '#ffffff'"
        :stroke="v.stroke || '#333333'"
        :stroke-width="v.stwidth || '0.3'"></path>
    </svg>
  </el-tooltip>
</template>

<style lang="scss" scoped>
svg {
  --uno: w-10 h-10 object-cover cursor-pointer;
}
</style>
