<script setup lang="ts">
/**
 * @description Flowchat 底部工具
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import * as Data from "@mainapp/data/diagraming/flowchart";

const paper = inject<Ref<dia.Paper>>(KeyVals.BLEUON_FLOWCHART_PAPER);

/**
 * 监听当 slider 组件变化时，重设置 paper 的 scale
 */
function onSliderChange() {
  paper.value.scale(Data.currentScale.value, Data.currentScale.value);
}

/**
 * 重置 slider 以及 paper sacle 为初始状态
 */
function resetSlider() {
  Data.currentScale.value = 1;
  paper.value.scale(Data.currentScale.value, Data.currentScale.value);
}
</script>

<template>
  <div
    class="bleuon__flowchat-footer-tools b-border-primary b-t-1 b-t-solid bg-bg-primary f-c-e px-4 text-0.8rem">
    <div class="scale-tool f-c-c">
      <div class="mr-4">
        <input
          type="range"
          @change="onSliderChange"
          v-model="Data.calcCurrentScale.value"
          min="20"
          max="200"
          step="1" />
      </div>
      <el-tooltip content="重置缩放" placement="top">
        <div class="cursor-pointer" @click="resetSlider">{{ Data.calcCurrentScale.value }}%</div>
      </el-tooltip>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
