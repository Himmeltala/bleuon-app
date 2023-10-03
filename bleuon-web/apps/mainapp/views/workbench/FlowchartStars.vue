<script setup lang="ts">
/**
 * @description 收藏的流程图
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { FlowchartApi } from "@mainapp/apis";
import { formatted } from "@common/utils/date";
import { downloadWithDataUri } from "@mainapp/lib/tools";

import Header from "@mainapp/components/workbench/Header.vue";
import File from "@mainapp/components/workbench/File.vue";

const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);
const collect = shallowRef();
const searchVal = ref("");

async function fetchData(params?: any) {
  collect.value = await FlowchartApi.findAllCollect({ ...params, ...{ uid: token.id } });
}

async function searchFiles() {
  fetchData({ fileName: searchVal.value });
}

function downloadFlowchart(data: any) {
  downloadWithDataUri(
    data,
    "jpeg",
    (res: string) => ElMessage.success(res),
    (res: string) => ElMessage.error(res)
  );
}

function cloneFlowchart(data: any) {
  data.fileName = "复制_收藏_" + data.fileName;
  FlowchartApi.cloneOne(data, () => {
    ElMessage.success("复制成功，在流程图中查看！");
  });
}

function deleteFlowchart(id: string, index: number) {
  FlowchartApi.deleteOneCollect({ id }, () => {
    collect.value.splice(index, 1);
    triggerRef(collect);
  });
}

await fetchData();
</script>

<template>
  <div class="flowchart-stars">
    <Header v-model:value="searchVal" @enter-search="searchFiles"></Header>
    <div class="file-list mt-5 f-c-s flex-wrap flex-gap-5">
      <template v-if="collect">
        <File
          v-for="(item, index) in collect"
          :key="item.flowchart.id"
          :file-name="item.flowchart.fileName"
          :file-image="item.flowchart.dataUri"
          :modify-date="formatted('MM-dd HH:mm:ss', new Date(item.flowchart.modifyDate))"
          :path="'/share/flowchart/' + item.flowchart.id"
          :is-reset="false"
          @clone="cloneFlowchart(item.flowchart)"
          @download="downloadFlowchart(item.flowchart)"
          @delete="deleteFlowchart(item.id, index)"></File>
      </template>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
