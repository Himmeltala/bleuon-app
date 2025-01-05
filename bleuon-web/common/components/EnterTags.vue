<script setup lang="ts">
import { TextUtil } from "@common/utils";

const props = defineProps({
  modelValue: {
    type: Array as PropType<string[]>,
    required: true
  },
  advance: {
    type: Array as PropType<{ value: string }[]>,
    required: false
  },
  limit: {
    type: Number,
    default: 5
  },
  contentMax: {
    type: Number,
    default: 5
  },
  contentMin: {
    type: Number,
    default: 3
  },
  width: {
    type: String
  }
});

const emits = defineEmits(["update:modelValue"]);

function initalData(data: string[]) {
  tagList.value = data;
}

defineExpose({ initalData });

const content = ref("");
const tagList = ref([]);

function onEnter() {
  const len = TextUtil.strlen(content.value);

  if (len < props.contentMin) {
    ElMessage.warning(`标签内容不能少于 ${props.contentMin} 个字`);
  } else if (len > props.contentMax) {
    ElMessage.warning(`标签内容不能超过 ${props.contentMax} 字`);
  } else if (tagList.value.length >= props.limit) {
    ElMessage.warning(`最多添加 ${props.limit} 个标签`);
  } else if (tagList.value.includes(content.value)) {
    ElMessage.warning("不可重复添加标签！");
  } else {
    tagList.value.push(content.value);
    emits("update:modelValue", tagList.value);
    content.value = "";
  }
}

const createFilter = (target: any) => (source: any) => source.value.includes(target);

const queryFromTagList = (target: any, filter: any) => {
  const result = target ? props.advance.filter(createFilter(target)) : props.advance;
  filter(result);
};

function closeTag(index: number) {
  tagList.value.splice(index, 1);
  emits("update:modelValue", tagList.value);
}
</script>

<template>
  <div class="enter-tags" :class="width">
    <el-autocomplete
      :class="width"
      v-if="advance"
      v-model="content"
      clearable
      :fetch-suggestions="queryFromTagList"
      :placeholder="'回车添加标签，最多添加 ' + limit + ' 个'"
      @keyup.enter="onEnter"
      @select="onEnter" />
    <el-input
      v-else
      :class="width"
      v-model="content"
      :placeholder="'回车添加标签，最多添加 ' + limit + ' 个'"
      clearable
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
