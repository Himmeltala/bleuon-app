<script lang="ts" setup>
/**
 * @description 工作台头部组件
 * @author zheng
 * @since 2023/8/23
 */

import MenuAvatar from "@mainapp/fragments/MenuAvatar.vue";

defineProps({
  value: {
    type: String,
    required: true
  }
});

const emits = defineEmits(["update:value", "enterSearch"]);

const searchVal = ref("");
const searchType = ref("file");

function onInput(value: string) {
  emits("update:value", value);
}

function onEnter() {
  emits("enterSearch", searchVal.value);
}
</script>

<template>
  <div class="header f-c-b py-5 px-10 mb-10 bg-bg-primary b-b-1 b-b-solid b-border-primary">
    <div class="w-120">
      <el-input
        v-model="searchVal"
        placeholder="搜索文件/文件夹"
        @input="onInput"
        @keyup.enter="onEnter">
        <template #suffix>
          <div class="i-tabler-search"></div>
        </template>
        <template #prepend>
          <el-select v-model="searchType" placeholder="搜索的类型" style="width: 115px">
            <el-option label="文件" value="file" />
            <!-- <el-option label="文件夹" value="folder" /> -->
          </el-select>
        </template>
      </el-input>
    </div>
    <div class="f-c-c">
      <div class="mr-5">
        <el-button bg size="small" text>
          <template #icon>
            <div class="i-tabler-bell"></div>
          </template>
        </el-button>
      </div>
      <MenuAvatar></MenuAvatar>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
