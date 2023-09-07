<script setup lang="ts">
import Avatar from "@mainapp/components/Avatar.vue";

const searchVal = ref("");
const searchType = ref("file");
const disabled = ref(false);
</script>

<template>
  <div class="header f-c-b mt-5 mb-10">
    <div class="w-120">
      <el-input v-model="searchVal" placeholder="搜索文件/文件夹">
        <template #suffix>
          <div class="i-tabler-search"></div>
        </template>
        <template #prepend>
          <el-select v-model="searchType" placeholder="搜索的类型" style="width: 115px">
            <el-option label="文件" value="file" />
            <el-option label="文件夹" value="folder" />
          </el-select>
        </template>
      </el-input>
    </div>
    <div class="f-c-c">
      <div class="mr-5">
        <el-button text bg size="small">
          <template #icon>
            <div class="i-tabler-bell"></div>
          </template>
        </el-button>
      </div>
      <Avatar></Avatar>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
