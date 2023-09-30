<script setup lang="ts">
/**
 * @description 用户的流程图
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import File from "./components/File.vue";
import Folder from "./components/Folder.vue";

const lastFileIndex = ref(-1);
const lastFolderIndex = ref(-1);

function filterFiles() {}

function sortFiles() {}
</script>

<template>
  <div class="myfiles">
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
    <div>
      <div class="mt-5 text-text-secondary text-0.9rem">文件夹</div>
      <div class="file-list mt-5 f-c-s flex-wrap flex-gap-5">
        <Folder
          :index="index"
          v-model:last-index="lastFolderIndex"
          :disabled="lastFolderIndex == index"
          :folder-name="'Demos' + index"
          v-for="(item, index) in 5"></Folder>
      </div>
    </div>
    <div>
      <div class="mt-5 text-text-secondary text-0.9rem">文件</div>
      <div class="file-list mt-5 f-c-s flex-wrap flex-gap-5">
        <File
          :file-name="'文件名'"
          :file-image="'https://img2.baidu.com/it/u=1616455932,108201296&fm=253&fmt=auto&app=138&f=JPEG?w=281&h=500'"
          :modify-date="'2月前'"
          :index="index"
          v-model:last-index="lastFileIndex"
          :disabled="lastFileIndex == index"
          v-for="(item, index) in 10"></File>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
