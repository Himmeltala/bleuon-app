<script lang="ts" setup>
/**
 * @description 收藏的流程图
 * @author zheng
 * @since 2023/8/23
 */

import { downloadWithDataURI } from "@common/lib/jointjs/utils";
import { DateUtil } from "@common/utils";
import { Requests } from "@common/requests";
import File from "@mainapp/components/File.vue";
import WorkbenchHeader from "@mainapp/fragments/workbench/WorkbenchHeader.vue";

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const mainData = ref<FlowchartModel[]>([]);
const searchVal = ref("");

async function fetchData(params?: any) {
  mainData.value = await Requests.Flowchart.findAllCollectByCriteria({
    ...params,
    collectorId: token.id
  });
}

async function search() {
  await fetchData({ filename: searchVal.value });
}

function download(data: FlowchartModel) {
  downloadWithDataURI(data.dataUri, data.filename, "jpeg");
}

function replicate(data: FlowchartModel) {
  Requests.Flowchart.replicate({ ...data, consumerId: token.id });
}

function remove(flowchartId: string, index: number) {
  Requests.Flowchart.deleteCollecting({ flowchartId, collectorId: token.id }, () => {
    mainData.value.splice(index, 1);
  });
}

function isMyFlowchart(item: FlowchartModel) {
  return item.consumer.id == token.id;
}

await fetchData();
</script>

<template>
  <div class="flowchart-stars max-h-100vh">
    <WorkbenchHeader v-model:value="searchVal" @enter-search="search"></WorkbenchHeader>
    <div class="px-10 pb-10">
      <div class="f-c-b">
        <div>收藏的流程图</div>
        <div></div>
      </div>
      <div class="mt-5 text-text-regular text-0.9rem">文件</div>
      <div class="file-list mt-5 f-c-s flex-wrap flex-gap-1.25rem">
        <template v-if="mainData">
          <File
            v-for="(item, index) in mainData"
            :key="item.id"
            :file-image="item.dataUri"
            :is-reset="false"
            :path="isMyFlowchart(item) ? '/flowchart/' + item.id : '/share/flowchart/' + item.id"
            :remove="() => remove(item.id, index)"
            :download="() => download(item)"
            :replicate="() => replicate(item)">
            <template #footer>
              <div class="f-c-s flex-nowrap mt-4 w-100%">
                <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
                <div class="text-0.9rem text-ellipsis line-clamp-1">{{ item.filename }}</div>
              </div>
              <div class="status mt-4">
                <el-tag size="small" v-if="item.isShare" :class="{ 'mr-2': item.isShare }">
                  分享
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-if="item.isPublic"
                  :class="{ 'mr-2': item.isPublic }">
                  公开
                </el-tag>
                <el-tag
                  size="small"
                  type="warning"
                  v-if="item.isBlueprint"
                  :class="{ 'mr-2': item.isBlueprint }">
                  模板
                </el-tag>
              </div>
              <div class="f-c-s text-text-secondary text-0.8rem mt-4">
                <img :src="item.consumer.avatar" class="mr-2 w-6 h-6 rd-50%" />
                <div>
                  {{ item.consumer.username }}
                </div>
              </div>
              <div class="text-0.8rem mt-4">
                <div class="text-text-regular">
                  创建:{{ DateUtil.formatted(item.createDate, "MM-dd HH:mm:ss") }}
                </div>
                <div class="text-text-secondary mt-1">
                  修改:{{ DateUtil.formatted(item.modifyDate, "MM-dd HH:mm:ss") }}
                </div>
              </div>
            </template>
          </File>
        </template>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
