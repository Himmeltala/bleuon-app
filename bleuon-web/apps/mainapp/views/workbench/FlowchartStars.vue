<script lang="ts" setup>
/**
 * @description 收藏的流程图
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { FlowchartApi } from "@mainapp/apis";
import { formatted } from "@common/utils/date";
import { downloadWithDataUri } from "@mainapp/lib/tools";

import WorkbenchHeader from "@mainapp/components/WorkbenchHeader.vue";
import File from "@mainapp/components/File.vue";

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

function deleteFlowchart(flowchartId: string, index: number) {
  FlowchartApi.deleteOneCollect({ flowchartId }, () => {
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
    <WorkbenchHeader v-model:value="searchVal" @enter-search="searchFiles"></WorkbenchHeader>
    <div class="file-list mt-5 f-c-s flex-wrap flex-gap-1.25rem">
      <template v-if="collect">
        <File
          v-for="(item, index) in collect"
          :key="item.id"
          :file-image="item.dataUri"
          :file-name="item.fileName"
          :is-reset="false"
          :path="isMyFlowchart(item) ? '/flowchart/' + item.id : '/share/flowchart/' + item.id"
          @clone="cloneFlowchart(item)"
          @delete="deleteFlowchart(item.id, index)"
          @download="downloadFlowchart(item)">
          <template #footer>
            <div class="f-c-s flex-nowrap mt-4 w-100%">
              <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
              <div class="text-0.9rem text-ellipsis line-clamp-1">{{ item.fileName }}</div>
            </div>
            <div class="text-text-secondary text-0.8rem mt-2 f-c-s">
              <div class="i-tabler-clock-edit mr-1"></div>
              {{ formatted("MM-dd HH:mm:ss", item.modifyDate) }}
            </div>
            <div class="f-c-s text-text-secondary text-0.8rem mt-2">
              <img :src="item.belongUser.avatar" class="mr-2 w-6 h-6 rd-50%" />
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

<style lang="scss" scoped></style>
