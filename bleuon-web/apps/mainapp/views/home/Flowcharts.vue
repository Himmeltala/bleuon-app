<script setup lang="ts">
/**
 * @description flowcharts 流程图列表
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import File from "@mainapp/components/home/File.vue";
import { FlowchartApi } from "@mainapp/apis";
import { downloadWithDataUri } from "@mainapp/lib/tools";
import { formatted } from "@common/utils/date";
import { fileNameValidator } from "@common/utils/form-validators";

import Header from "@mainapp/components/home/Header.vue";
import FilterFilesVue from "@mainapp/components/home/FilterFiles.vue";

const operateFlowchart = ref<FlowchartData>({});
const flowchartList = ref(await FlowchartApi.queryAll());
const lastFileIndex = ref(-1);

const updateFileNameDialogVisible = ref(false);
const isFileNameCorrect = ref(false);
const fileNameRules = reactive({
  fileName: [
    { validator: fileNameValidator(isFileNameCorrect), trigger: "blur" },
    { validator: fileNameValidator(isFileNameCorrect), trigger: "change" }
  ]
});

function updateFileName() {
  FlowchartApi.updateOne(operateFlowchart.value, () => {
    updateFileNameDialogVisible.value = !updateFileNameDialogVisible.value;
  });
}

function downloadFlowchart(data: FlowchartData) {
  downloadWithDataUri(
    data,
    "jpeg",
    () => {
      ElMessage.success("下载成功！");
    },
    () => {
      ElMessage.error("下载失败！");
    }
  );
}

function copyFlowchart(data: FlowchartData) {
  data.fileName = "复制_" + data.fileName;
  FlowchartApi.copyOne(data, async () => {
    flowchartList.value = await FlowchartApi.queryAll();
  });
}

function deleteFlowchart(id: string) {
  FlowchartApi.deleteOne({ id }, async () => {
    flowchartList.value = await FlowchartApi.queryAll();
  });
}

const searchVal = ref("");

async function searchFiles(fileName: string) {
  flowchartList.value = await FlowchartApi.queryAll({ fileName });
}
</script>

<template>
  <div class="myrecent h-100%">
    <Header v-model:value="searchVal" @enter-search="searchFiles"></Header>
    <FilterFilesVue title="我的流程图" />
    <div class="mt-5 text-text-secondary text-0.9rem">文件</div>
    <div class="file-list mt-5 f-c-s flex-wrap flex-gap-5">
      <File
        v-for="(item, index) in flowchartList"
        :key="item.id"
        :index="index"
        :disabled="lastFileIndex == index"
        v-model:last-index="lastFileIndex"
        :file-name="item.fileName"
        :file-image="item.dataUri"
        :modify-date="formatted('MM-dd HH:mm:ss', new Date(item.modifyDate))"
        :path="'/flowchart/' + item.id"
        @download="downloadFlowchart(item)"
        @copy="copyFlowchart(item)"
        @delete="deleteFlowchart(item.id)"
        @reset-file-name="updateFileNameDialogVisible = !updateFileNameDialogVisible"></File>
    </div>
    <el-dialog v-model="updateFileNameDialogVisible" title="修改文件名称" width="30%">
      <el-form :model="operateFlowchart" :rules="fileNameRules">
        <el-form-item prop="fileName">
          <el-input v-model="operateFlowchart.fileName" placeholder="请输入文件名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button :disabled="!isFileNameCorrect" type="primary" @click="updateFileName">
            <template #icon>
              <div class="i-tabler-check"></div>
            </template>
            确定
          </el-button>
          <el-button @click="updateFileNameDialogVisible = false">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
