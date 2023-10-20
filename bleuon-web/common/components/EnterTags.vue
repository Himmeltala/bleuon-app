<script setup lang="ts">
import { TextUtil } from "@common/utils";

const props = defineProps({
  modelValue: {
    type: Array as PropType<string[]>,
    required: true
  },
  max: {
    type: Number,
    default: 5
  }
});

const emits = defineEmits(["update:modelValue"]);

const content = ref("");
const tagList = ref([]);

function onEnter() {
  const len = TextUtil.strlen(content.value);

  if (len < 3) {
    ElMessage.warning("标签内容不能少于 3 个字");
    return;
  }

  if (len > 10) {
    ElMessage.warning("标签内容不能超过 10 个字");
    return;
  }

  if (tagList.value.length < props.max) {
    if (!tagList.value.includes(content.value)) {
      tagList.value.push(content.value);
      emits("update:modelValue", tagList.value);
    } else {
      ElMessage.warning("不可重复添加标签！");
    }
  } else {
    ElMessage.warning("最多添加 " + props.max + " 个标签");
  }
}

function closeTag(index: number) {
  tagList.value.splice(index, 1);
}
</script>

<template>
  <div class="enter-tags">
    <el-input
      v-model="content"
      :placeholder="'回车添加标签，最多添加 ' + max + ' 个'"
      @keyup.enter="onEnter" />
    <div class="f-c-s mt-2">
      <el-tag
        v-for="(item, index) in tagList"
        :key="item"
        class="mr-2"
        closable
        @close="closeTag(index)">
        {{ item }}
      </el-tag>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
