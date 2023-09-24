<script setup lang="ts">
/**
 * @description Flowchart 图形创建组件
 * @author 郑人滏 42020306
 * @since 2023/9/25
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import SVG from "./SVG.vue";

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
    <div class="mb-4 font-bold">Flowchart 图形</div>
    <div class="f-c-s flex-wrap flex-gap-5">
      <SVG
        :graph="graph"
        content="流程"
        view-box="-0.3 -0.3 13.6 7.6"
        d="M 0 0 L 13 0 L 13 7 L 0 7 Z"></SVG>
      <SVG
        :graph="graph"
        content="判定"
        view-box="-7.3 -0.3 14.6 9.6"
        d="M 0 0 L 7 5 L 0 9 L -7 4 Z"></SVG>
      <SVG
        :graph="graph"
        content="开始/结束"
        view-box="-8.8 -0.3 11.6 5.6"
        d="M -6 0 A 1 1 0 0 0 -6 5 H 0 A 1 1 0 0 0 0 0 H -6 Z"></SVG>
      <SVG
        :graph="graph"
        content="文档"
        view-box="-0.3 -0.3 20.6 10.26"
        stroke-width="0.4"
        d="M 0 0 L 20 0 L 20 9 C 16 7 15 7 11 8 C 5 10 4 10 0 9 Z"></SVG>
      <SVG
        :graph="graph"
        content="数据"
        stroke-width="0.4"
        view-box="-3.3 -0.3 23.6 11.6"
        d="M 0 0 L 20 0 L 17 11 L -3 11 Z"></SVG>
      <SVG
        :graph="graph"
        content="子流程"
        view-box="-2.3 -0.3 18.6 10.6"
        d="M 0 0 V 10 H 14 V 0 Z M 16 0 L 16 0 V 10 H 14 V 0 Z M -2 0 V 10 H 0 V 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="外部数据"
        view-box="-1.8 -0.3 13.1 7.6"
        d="M 0 0 H 11 C 9 2 9 5 11 7 H 0 C -2 5 -2 2 0 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="内部存储"
        view-box="-0.3 -0.3 13.6 8.6"
        d="M 0 0 H 2 V 2 H 0 Z M 2 0 H 13 V 2 H 2 Z M 0 2 V 8 H 2 V 2 Z M 13 8 V 2 H 2 V 8 Z"></SVG>
      <SVG
        :graph="graph"
        content="队列数据"
        stroke-width="0.2"
        :width="70"
        :height="70"
        view-box="-4.3 -0.3 8.6 8.6"
        d="M 0 0 A 1 1 0 0 0 0 8 A 1 1 0 0 0 0 0 Z M 0 8 L 4 8 Z"></SVG>
      <SVG
        :graph="graph"
        content="横向数据库"
        view-box="-1.05 -0.3 13.1 6.6"
        d="M 0 0 H 11 C 10 1 10 5 11 6 H 0 C -1 5 -1 1 0 0 Z M 11 0 C 12 1 12 5 11 6 C 10 5 10 1 11 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="竖向数据库"
        :height="140"
        :width="70"
        view-box="-0.3 -1.05 8.6 12.1"
        d="M 0 0 L 0 10 C 1 11 7 11 8 10 L 8 0 C 7 1 1 1 0 0 M 0 0 C 1 -1 7 -1 8 0 C 7 1 1 1 0 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="人工输入"
        view-box="-0.3 -2.3 13.6 8.6"
        d="M 0 0 L 13 -2 V 6 H 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="卡片"
        view-box="-0.3 -0.3 11.6 8.6"
        d="M 0 3 V 8 H 11 V 0 H 5 L 0 3 Z"></SVG>
      <SVG
        :graph="graph"
        content="条带"
        view-box="-0.3 -0.3 12.6 6.88"
        d="M 0 0 V 5 C 4 5 8 7 12 6 V 1 C 8 2 4 0 0 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="展示"
        view-box="-1.5 -1.5 12 11"
        d="M 0 4 L 2 0 H 9 C 9 3 9 5 9 8 H 2 L 0 4 Z"></SVG>
      <SVG
        :graph="graph"
        content="人工操作"
        view-box="-0.3 -0.3 9.6 5.6"
        d="M 0 0 H 9 L 7 5 H 2 L 0 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="预备"
        view-box="-2.3 -0.3 11.6 6.6"
        d="M 0 0 L 7 0 L 9 3 L 7 6 L 0 6 L -2 3 Z"></SVG>
      <SVG
        :graph="graph"
        content="并行模式"
        view-box="-0.3 -1.3 11.6 8.6"
        d="M 0 0 H 11 L 11 6 H 0 L 0 0 M 0 -1 L 11 -1 Z M 0 7 L 11 7 Z"></SVG>
      <SVG
        :graph="graph"
        content="循环限值"
        stroke-width="0.25"
        view-box="0.7 -0.3 8.6 6.6"
        d="M 3 0 H 7 L 9 2 V 6 H 1 V 2 Z"></SVG>
      <SVG
        :graph="graph"
        content="或者"
        stroke-width="0.15"
        view-box="-3.3 -0.3 6.6 6.6"
        d="M 0 0 A 1 1 0 0 0 0 6 A 1 1 0 0 0 0 0 Z M -3 3 L 3 3 Z M 0 0 L 0 6 Z"></SVG>
      <SVG
        :graph="graph"
        content="求和"
        stroke-width="0.15"
        view-box="-3.3 -0.3 6.6 6.6"
        d="M 0 0 A 1 1 0 0 0 0 6 A 1 1 0 0 0 0 0 Z M -2.5 1.4 L 2.5 4.6 Z M 2.5 1.4 L -2.5 4.6"></SVG>
      <SVG
        :graph="graph"
        content="跨页引用"
        stroke-width="0.2"
        view-box="-0.3 -0.3 6.6 5.6"
        d="M 0 0 H 6 V 3 L 3 5 L 0 3 Z"></SVG>
      <SVG
        :graph="graph"
        content="页面内引用"
        stroke-width="0.15"
        view-box="-3.3 -0.3 6.6 6.6"
        d="M 0 0 A 1 1 0 0 0 0 6 A 1 1 0 0 0 0 0 Z"></SVG>
      <SVG
        :graph="graph"
        content="排序"
        stroke-width="0.2"
        view-box="-0.3 -0.3 8.6 8.6"
        d="M 4 0 L 0 4 L 4 8 L 8 4 Z M 0 4 L 8 4 Z"></SVG>
      <SVG
        :graph="graph"
        content="合并"
        stroke-width="0.15"
        view-box="-0.2 -0.2 6.4 5.4"
        d="M 0 0 H 6 L 3 5 Z"></SVG>
      <SVG
        :graph="graph"
        content="备注1"
        view-box="-1 -4 2 14"
        d="M 1 -4 Q 0 -4 0 -3 V 2 C 0 3 -1 3 -1 3 C 0 3 0 4 0 4 V 9 Q 0 10 1 10"></SVG>
      <SVG
        :graph="graph"
        content="备注2"
        view-box="0 -4 2 14"
        d="M 0 -4 Q 1 -4 1 -3 V 2 C 1 3 2 3 2 3 C 1 3 1 4 1 4 V 9 Q 1 10 0 10"></SVG>
      <SVG
        :graph="graph"
        content="注释1"
        view-box="0 -4 1 14"
        d="M 1 -4 L 0 -4 V 3 V 10 L 1 10"></SVG>
      <SVG
        :graph="graph"
        content="注释2"
        view-box="1 -4 1 14"
        d="M 1 -4 L 2 -4 V 3 V 10 L 1 10"></SVG>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
