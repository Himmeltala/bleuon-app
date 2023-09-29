<script setup lang="ts">
import { dia } from "@mainapp/lib";
import { convertSvgToImage } from "@mainapp/lib/tools";
import MenuAvatar from "@mainapp/components/MenuAvatar.vue";
import { formatted } from "@common/utils/date";

const paper = inject<Ref<dia.Paper>>(KeyVals.BLEUON_FLOWCHART_PAPER);
const graph = inject<Ref<dia.Graph>>(KeyVals.BLEUON_FLOWCHART_GRAPH);
const flowchartData = inject<Ref<FlowchartData>>(KeyVals.BLEUON_FLOWCHART_DATA);

const editFile = ref(false);

defineProps({
  data: {
    type: Object
  }
});

function download() {
  const { width, height } = paper.value.getArea();
  flowchartData.value.width = width;
  flowchartData.value.height = height;
  convertSvgToImage(paper.value, graph.value, "jpeg", flowchartData.value);
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
</script>

<template>
  <div class="border-#dfe2e5 border-b-1 border-b-solid f-c-b pb-4">
    <div class="left f-c-s">
      <el-button @click="$router.back()" type="primary" bg text>
        <template #icon>
          <div class="i-tabler-chevron-left"></div>
        </template>
      </el-button>
      <img
        @click="$router.push('/home')"
        src="/bleuon-icon.png"
        class="mr-4 w-30 h-15 object-cover cursor-pointer" />
      <div>
        <div v-show="editFile" @keyup.enter="editFile = !editFile">
          <el-input v-model="calcFileName" size="small" placeholder="请输入文件名" />
        </div>
        <div class="text-gray-700" v-show="!editFile" @click="editFile = !editFile">
          {{ calcFileName }}
        </div>
        <div class="mt-2">
          <div class="text-gray-500 text-0.8rem">
            上次更新时间：{{ formatted("yyyy-MM-dd HH:mm:ss", new Date(flowchartData.modifyDate)) }}
          </div>
        </div>
      </div>
    </div>
    <div class="right f-c-s">
      <div class="mr-6 f-c-s text-gray-500 text-1.5rem">
        <div @click="download" class="hover mr-4 i-tabler-download"></div>
        <div class="hover i-tabler-share"></div>
      </div>
      <MenuAvatar />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
