<script lang="ts" setup>
/**
 * @description 流程图头部工具
 * @author zheng
 * @since 2023/10/1
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import { convertSvgToImage } from "@mainapp/lib/tools";
import { DateUtil } from "@common/utils";
import { FlowchartApi } from "@mainapp/apis";

// components
import MenuAvatar from "@mainapp/components/MenuAvatar.vue";

const paper = inject<Ref<dia.Paper>>(KeyVals.BLEUON_FLOWCHART_PAPER);
const graph = inject<Ref<dia.Graph>>(KeyVals.BLEUON_FLOWCHART_GRAPH);
const flowchartData = inject<Ref<FlowchartModel>>(KeyVals.BLEUON_FLOWCHART_DATA);
const token = localStorage.getToken(KeyVals.MAINAPP_TOKEN_KEY);

const emits = defineEmits(["change"]);

function download() {
  const { width, height } = paper.value.getArea();
  flowchartData.value.width = width;
  flowchartData.value.height = height;
  convertSvgToImage(
    paper.value,
    graph.value,
    "jpeg",
    flowchartData.value,
    res => ElMessage.success(res),
    res => ElMessage.error(res)
  );
}

const calcFileName = computed({
  get() {
    if (!flowchartData.value.fileName) {
      flowchartData.value.fileName = "未命名的文件";
    }
    return flowchartData.value.fileName;
  },
  set(value) {
    flowchartData.value.fileName = value;
  }
});

function onEnterFileName() {
  emits("change");
}

function collectFlowchart() {
  FlowchartApi.addCollect({ flowchartId: flowchartData.value.id, collectingCid: token.id });
}
</script>

<template>
  <div class="b-border-primary bg-bg-primary b-b-1 b-b-solid f-c-b pb-4">
    <div class="left f-c-s">
      <el-button bg text type="primary" @click="$router.back()">
        <template #icon>
          <div class="i-tabler-chevron-left"></div>
        </template>
      </el-button>
      <router-link to="/workbench">
        <img class="mr-4 w-30 h-15 object-cover cursor-pointer" src="/bleuon-icon.png" />
      </router-link>
      <div>
        <EditInput
          v-model:text="calcFileName"
          :base-modification="true"
          placeholder="请输入文件名"
          size="small"
          @enter="onEnterFileName"></EditInput>
        <div class="mt-2">
          <div class="text-text-secondary text-0.8rem f-c-c">
            <div class="i-tabler-clock mr-1"></div>
            上次更新：{{ DateUtil.formatted(flowchartData.modifyDate) }}
          </div>
        </div>
      </div>
    </div>
    <div class="right f-c-s">
      <div class="mr-6 f-c-s text-text-secondary text-1.5rem">
        <el-tooltip content="下载">
          <div class="hover mr-4 i-tabler-download" @click="download"></div>
        </el-tooltip>
        <el-tooltip v-if="token" content="收藏">
          <div class="hover mr-4 i-tabler-star" @click="collectFlowchart"></div>
        </el-tooltip>
        <slot name="tools"></slot>
      </div>
      <MenuAvatar v-if="token" />
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
