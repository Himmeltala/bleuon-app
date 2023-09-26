<script setup lang="ts">
/**
 * @description 基础图形创建组件
 * @author 郑人滏 42020306
 * @since 2023/9/25
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import SVG from "./SVG.vue";

defineProps({
  graph: {
    type: dia.Graph
  }
});
</script>

<template>
  <div class="cells-for-flowchart">
    <div class="mb-4 font-bold">基础图形</div>
    <div class="f-c-s flex-wrap flex-gap-5">
      <SVG
        :graph="graph"
        content="矩形"
        view-box="-0.3 -0.3 13.6 8.6"
        d="M 0 0 H 13 V 8 H 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="圆角矩形"
        view-box="-0.3 -0.3 12.6 7.6"
        d="M 1 0 H 11 Q 12 0 12 1 V 6 Q 12 7 11 7 H 1 Q 0 7 0 6 V 1 Q 0 0 1 0"></SVG>
      <SVG
        :graph="graph"
        content="三角形"
        view-box="-0.3 -0.3 12.6 8.6"
        d="M 6 0 L 0 8 H 12 Z"></SVG>
      <SVG
        :graph="graph"
        content="圆形"
        stroke-width="0.2"
        view-box="0.2 -0.3 7.6 7.6"
        d="M 4 7 A 1 1 0 0 0 4 0 A 1 1 0 0 0 4 7"></SVG>
      <SVG
        :graph="graph"
        content="直角三角形"
        stroke-width="0.2"
        view-box="-0.3 -0.3 10.6 8.6"
        d="M 0 0 V 8 H 10 Z"></SVG>
      <SVG
        :graph="graph"
        content="菱形"
        view-box="-7.3 -0.3 14.6 9.6"
        d="M 0 0 L 7 5 L 0 9 L -7 4 Z"></SVG>
      <SVG
        :graph="graph"
        content="五边形"
        :width="90"
        :height="90"
        view-box="1.7 -0.3 11.6 10.6"
        d="M 7.5 0 L 2 4.5 L 4 10 H 11 L 13 4.5 Z"></SVG>
      <SVG
        :graph="graph"
        content="六边形"
        :width="90"
        :height="90"
        view-box="1.7 -0.3 11.6 9.6"
        d="M 4 0 H 11 L 13 4.5 L 11 9 H 4 L 2 4.5 Z"></SVG>
      <SVG
        :graph="graph"
        content="八边形"
        :width="90"
        :height="90"
        stroke-width="0.2"
        view-box="1.7 -0.3 8.6 9.1"
        d="M 4 0 H 8 L 10 2.5 V 6 L 8 8.5 H 4 L 2 6 V 2.5 Z"></SVG>
      <SVG
        :graph="graph"
        content="云"
        stroke-width="0.2"
        view-box="1.1781 -0.6327 12.03 9.352"
        d="M 2.509 2.003 C 2.273 1.01 3.371 0.121 5 1 C 5 0 6 -1 7.528 0.252 C 9 -1 10 0 10 1 C 11.475 0.984 13 1 12 4 C 13.724 5.297 12.835 7.284 10.717 7.31 C 10 8 8.6 8.983 7 8 C 5 9 3 8 3.371 7.205 C 2 7 1.332 5.768 2.509 4.8 C 1 4 1.332 3.101 2.326 2.265 Z"></SVG>
      <SVG
        :graph="graph"
        content="引用"
        :width="140"
        :height="100"
        stroke-width="0.2"
        view-box="2.679 3.2786 14.22 10.21"
        d="M 5.061 10.183 C 4.601 7.5 5 6.3333 5.831 5.377 C 7.189 4.071 8.157 3.769 10.062 3.61 C 12.276 3.472 13.603 3.785 14.809 4.626 C 16.905 6.659 16.953 8.484 16.08 10.374 C 14.841 12.374 13.047 13.073 11.253 13.168 C 8.395 13.311 6.823 12.612 5.807 11.437 L 2.997 12.612 Z"></SVG>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
