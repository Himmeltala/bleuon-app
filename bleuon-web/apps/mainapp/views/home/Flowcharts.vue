<script setup lang="ts">
/**
 * @description flowcharts 流程图列表
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import File from "./components/File.vue";
import { FlowchartApi } from "@mainapp/apis";
import { formatted } from "@common/utils/date";

const lastFileIndex = ref(-1);

const flowchartList = ref(await FlowchartApi.queryAll());

function filterFiles() {}
</script>

<template>
  <div class="myrecent h-100%">
    <div class="f-c-b">
      <div>最近</div>
      <div class="f-c-c">
        <div>
          <el-tooltip content="筛选" placement="bottom">
            <el-button @click="filterFiles" size="small">
              <template #icon>
                <div class="i-tabler-filter"></div>
              </template>
            </el-button>
          </el-tooltip>
        </div>
      </div>
    </div>
    <div class="mt-5 text-b text-0.9rem">文件</div>
    <div class="file-list mt-5 f-c-s flex-wrap flex-gap-5">
      <File
        v-for="(item, index) in flowchartList"
        :key="item.id"
        :file-name="item.fileName"
        :file-image="'https://img2.baidu.com/it/u=1616455932,108201296&fm=253&fmt=auto&app=138&f=JPEG?w=281&h=500'"
        :update-date="formatted('yyyy-MM-dd HH:mm:ss', new Date(item.modifyDate))"
        :index="index"
        :path="'/flowchart/' + item.id"
        v-model:last-index="lastFileIndex"
        :disabled="lastFileIndex == index"></File>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
