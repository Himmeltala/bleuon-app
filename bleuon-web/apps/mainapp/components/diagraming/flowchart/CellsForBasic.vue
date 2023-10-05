<script lang="ts" setup>
/**
 * @description 基础图形创建组件
 * @author 郑人滏 42020306
 * @since 2023/9/25
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { OfficialCellsApi } from "@mainapp/apis";
import SVG from "../SVG.vue";

const data = ref(null);

data.value = await OfficialCellsApi.findAll("basic");
</script>

<template>
  <div class="cells-for-flowchart">
    <div class="mb-4 text-0.8rem font-bold">基础图形</div>
    <div class="f-c-s flex-wrap flex-gap-3">
      <SVG
        v-for="item in data"
        v-if="data.length"
        :attrs="JSON.parse(item.attrs)"
        :content="item.notes"
        :height="item.height"
        :view-box="item.viewBox"
        :width="item.width"></SVG>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
