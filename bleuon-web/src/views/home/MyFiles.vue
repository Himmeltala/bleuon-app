<script setup lang="ts">
import File from "./components/File.vue";

const fileIndex = ref(-1);

function filterFiles() {}

function sortFiles() {}
</script>

<template>
  <div>
    <div class="f-c-b">
      <div>我的文件</div>
      <div class="f-c-c">
        <div class="mr-5">
          <el-tooltip content="筛选" placement="bottom">
            <el-button @click="filterFiles" size="small">
              <template #icon>
                <div class="i-tabler-filter"></div>
              </template>
            </el-button>
          </el-tooltip>
        </div>
        <div>
          <el-tooltip content="排序" placement="bottom">
            <el-button @click="sortFiles" size="small">
              <template #icon>
                <div class="i-tabler-arrows-sort"></div>
              </template>
            </el-button>
          </el-tooltip>
        </div>
      </div>
    </div>
    <div class="mt-5 text-b text-0.9rem">文件</div>
    <div class="file-list mt-5 f-c-s flex-wrap flex-gap-5">
      <File
        :index="index"
        v-model:clicked-index="fileIndex"
        :disabled-ops="fileIndex == index"
        v-for="(item, index) in 5"></File>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
