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

const collect = shallowRef();
const searchVal = ref("");

async function fetchData(params?: any) {
  collect.value = await FlowchartApi.findAllCollect(params);
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

const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);

function isMyFlowchart(item: any) {
  return item.belongUser.id == token.id;
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
          :key="item.id"
          :file-name="item.fileName"
          :file-image="item.dataUri"
          :modify-date="formatted('MM-dd HH:mm:ss', new Date(item.modifyDate))"
          :path="isMyFlowchart(item) ? '/flowchart/' + item.id : '/share/flowchart/' + item.id"
          :is-reset="false"
          @clone="cloneFlowchart(item)"
          @download="downloadFlowchart(item)"
          @delete="deleteFlowchart(item.id, index)">
          <template #footer>
            <div class="f-c-s text-text-secondary text-0.8rem mt-2">
              <img class="mr-2 w-6 h-6 rd-50%" :src="item.belongUser.avatar" />
              <div>
                {{ isMyFlowchart(item) ? "我的" : item.belongUser.username }}
              </div>
            </div>
          </template>
        </File>
      </template>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
