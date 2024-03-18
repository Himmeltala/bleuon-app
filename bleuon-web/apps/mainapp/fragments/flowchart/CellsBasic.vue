<script lang="ts" setup>
/**
 * @description 基础图形创建组件
 * @author zheng
 * @since 2023/9/25
 */

import { Requests } from "@common/requests";
import Plot from "./Plot.vue";

const data = ref();
data.value = await Requests.Cell.findAllByCriteria({ type: "basic" });
</script>

<template>
  <div class="cells-for-flowchart">
    <div class="mb-4 text-0.8rem font-bold">基础图形</div>
    <div class="f-c-s flex-wrap flex-gap-3">
      <Plot
        v-for="item in data"
        v-if="data.length"
        :attrs="JSON.parse(item.attrs)"
        :content="item.notes"
        :height="item.height"
        :view-box="item.viewBox"
        :width="item.width"></Plot>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
./Svg.vue
