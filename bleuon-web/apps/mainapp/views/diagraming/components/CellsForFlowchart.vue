<script setup lang="ts">
/**
 * @description Flowchart 图形创建组件
 * @author 郑人滏 42020306
 * @since 2023/9/25
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { queryAllCells } from "@mainapp/apis/api-official-cells";
import SVG from "./SVG.vue";

const data = ref(null);

data.value = await queryAllCells("flowchart");
</script>

<template>
  <div class="cells-for-flowchart">
    <div class="mb-4 font-bold">Flowchart 图形</div>
    <div class="f-c-s flex-wrap flex-gap-3">
      <SVG
        v-if="data.length"
        v-for="item in data"
        :width="item.width"
        :height="item.height"
        :content="item.notes"
        :view-box="item.viewBox"
        :attrs="JSON.parse(item.attrs)"></SVG>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
