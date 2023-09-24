<script setup lang="ts">
/**
 * @description Flowchat 底部工具
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import * as Data from "../data";

const props = defineProps({
  paper: {
    type: dia.Paper
  },
  graph: {
    type: dia.Graph
  }
});

/**
 * 监听当 slider 组件变化时，重设置 paper 的 scale
 */
function onSliderChange() {
  props.paper.scale(Data.currentScale.value, Data.currentScale.value);
}

/**
 * 重置 slider 以及 paper sacle 为初始状态
 */
function resetSlider() {
  Data.currentScale.value = 1;
  props.paper.scale(Data.currentScale.value, Data.currentScale.value);
}
</script>

<template>
  <div
    class="bleuon__flowchat-footer-tools border-#dfe2e5 border-t-1 border-t-solid bg-#f6f7f8 f-c-e px-4">
    <div class="scale-tool f-c-c">
      <div class="w-50 mr-4">
        <el-slider
          @change="onSliderChange"
          v-model="Data.calcCurrentScale.value"
          size="small"
          :min="80"
          :max="200" />
      </div>
      <el-tooltip content="重置缩放" placement="top">
        <div class="cursor-pointer" @click="resetSlider">{{ Data.calcCurrentScale.value }}%</div>
      </el-tooltip>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
