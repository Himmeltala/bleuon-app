<script lang="ts" setup>
/**
 * @description Flowchat 底部工具
 * @author zheng
 * @since 2023/9/24
 */

import { dia } from "jointjs";

const props = defineProps({
  scale: {
    type: Number,
    required: true
  }
});
const emits = defineEmits(["update:scale"]);

const clearWatcher = watch(
  () => props.scale,
  newVal => {
    scale.value = newVal;
  }
);

onBeforeRouteLeave(() => {
  clearWatcher();
});

const scale = ref(props.scale);
const paper = inject<Ref<dia.Paper>>(Consts.BLEUON_FLOWCHART_PAPER);

const formateScale = computed({
  get() {
    return Number((scale.value * 100).toFixed(0));
  },
  set(value) {
    scale.value = value / 100;
    emits("update:scale", scale.value);
  }
});

function onRangeChange() {
  emits("update:scale", scale.value);
  paper.value.scale(scale.value, scale.value);
}

function resetRange() {
  scale.value = 1;
  emits("update:scale", scale.value);
  paper.value.scale(scale.value, scale.value);
}
</script>

<template>
  <div
    class="bleuon__flowchat-footer-tools b-border-primary b-t-1 b-t-solid bg-bg-primary f-c-e px-4 text-0.8rem">
    <div class="scale-tool f-c-c">
      <div class="mr-4">
        <input
          v-model="formateScale"
          max="200"
          min="20"
          step="1"
          type="range"
          @change="onRangeChange" />
      </div>
      <el-tooltip content="重置缩放" placement="top">
        <div class="cursor-pointer" @click="resetRange">{{ formateScale }}%</div>
      </el-tooltip>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
