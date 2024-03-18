<script lang="ts" setup>
/**
 * @description 流程图头部工具
 * @author zheng
 * @since 2023/10/1
 */

import { dia } from "jointjs";
import { downloadWithDataURI, getDataURI } from "@common/lib/jointjs/utils";
import { DateUtil } from "@common/utils";
import { Requests } from "@common/requests";

// components
import MenuAvatar from "@mainapp/fragments/MenuAvatar.vue";

const paper = inject<Ref<dia.Paper>>(Consts.BLEUON_FLOWCHART_PAPER);
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const mainData = inject<Ref<FlowchartModel>>(Consts.BLEUON_FLOWCHART_DATA);

const emits = defineEmits(["change"]);

async function download() {
  const { width, height } = paper.value.getArea();
  mainData.value.width = width;
  mainData.value.height = height;
  const dataURI = await getDataURI(paper.value);
  downloadWithDataURI(dataURI, mainData.value.filename, "jpeg");
}

const calcFileName = computed({
  get() {
    if (!mainData.value.filename) {
      mainData.value.filename = "未命名的文件";
    }
    return mainData.value.filename;
  },
  set(value) {
    mainData.value.filename = value;
  }
});

function collect() {
  Requests.Flowchart.addCollecting({ flowchartId: mainData.value.id, collectorId: token.id });
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
          @enter="$emit('change')"></EditInput>
        <div class="mt-2">
          <div class="text-text-secondary text-0.8rem f-c-c">
            <div class="i-tabler-clock mr-1"></div>
            上次更新：{{ DateUtil.formatted(mainData.modifyDate) }}
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
          <div class="hover mr-4 i-tabler-star" @click="collect"></div>
        </el-tooltip>
        <slot name="tools"></slot>
      </div>
      <MenuAvatar v-if="token" />
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
