<script lang="ts" setup>
/**
 * @description flowcharts 流程图列表
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { FlowchartApi } from "@mainapp/apis";
import { downloadWithDataUri } from "@mainapp/lib/tools";
import { formatted } from "@common/utils/date";
import { fileNameValidator } from "@common/utils/form-validators";

import WorkbenchHeader from "@mainapp/components/WorkbenchHeader.vue";
import File from "@mainapp/components/File.vue";

const clickedIndex = ref(0);
const flowchartList = ref(await FlowchartApi.findAll());

const dialogVisible = ref(false);
const isFileNameCorrect = ref(false);
const fileNameRules = reactive({
  fileName: [
    { validator: fileNameValidator(isFileNameCorrect), trigger: "blur" },
    { validator: fileNameValidator(isFileNameCorrect), trigger: "change" }
  ]
});

function updateFlowchart() {
  FlowchartApi.updateOne(flowchartList.value[clickedIndex.value], () => {
    dialogVisible.value = !dialogVisible.value;
  });
}

function resetFlowchart(index: number) {
  dialogVisible.value = !dialogVisible.value;
  clickedIndex.value = index;
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

function cloneFlowchart(data: FlowchartData) {
  data.fileName = "复制_" + data.fileName;
  FlowchartApi.cloneOne(data, async () => {
    await fechData();
  });
}

function deleteFlowchart(id: string, index: number) {
  FlowchartApi.deleteOne({ id }, async () => {
    flowchartList.value.splice(index, 1);
  });
}

async function fechData(params?: any) {
  flowchartList.value = await FlowchartApi.findAll(params);
}

const searchVal = ref("");
const isModifyDateAsc = ref(false);
const isCreateDateAsc = ref(false);

function allFlowchart() {
  fechData({
    fileName: searchVal.value
  });
}

function publicFlowchart() {
  fechData({
    fileName: searchVal.value,
    isPublic: 1
  });
}

function shareFlowchart() {
  fechData({
    fileName: searchVal.value,
    isShare: 1
  });
}

function legalFlowchart() {
  fechData({
    fileName: searchVal.value,
    isLegal: 1
  });
}

function modifyDateFlowchart() {
  isModifyDateAsc.value = !isModifyDateAsc.value;
  fechData({ collates: [{ col: "modify_date", isAsc: isModifyDateAsc.value }] });
}

function createDateFlowchart() {
  isCreateDateAsc.value = !isCreateDateAsc.value;
  fechData({ collates: [{ col: "create_date", isAsc: isCreateDateAsc.value }] });
}

async function searchFiles() {
  fechData({ fileName: searchVal.value });
}
</script>

<template>
  <div class="flowchart-list h-100vh">
    <WorkbenchHeader v-model:value="searchVal" @enter-search="searchFiles"></WorkbenchHeader>
    <div class="f-c-b">
      <div>流程图</div>
      <div class="f-c-c">
        <div>
          <el-tooltip content="筛选" placement="top">
            <el-dropdown :hide-on-click="false" :teleported="false" trigger="click">
              <el-button size="small" @click="">
                <template #icon>
                  <div class="i-tabler-filter"></div>
                </template>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="modifyDateFlowchart">
                    <div class="text-0.8rem f-c-s select-none">
                      <div
                        :class="
                          isModifyDateAsc
                            ? 'i-tabler-sort-ascending-2'
                            : 'i-tabler-sort-descending-2'
                        "
                        class="mr-2"></div>
                      <span>
                        修改日期<span v-if="isModifyDateAsc">升序</span><span v-else>降序</span>
                      </span>
                    </div>
                  </el-dropdown-item>
                  <el-dropdown-item @click="createDateFlowchart">
                    <div class="text-0.8rem f-c-s select-none">
                      <div
                        :class="
                          isCreateDateAsc
                            ? 'i-tabler-sort-ascending-2'
                            : 'i-tabler-sort-descending-2'
                        "
                        class="mr-2"></div>
                      <span>
                        创建日期<span v-if="isCreateDateAsc">升序</span><span v-else>降序</span>
                      </span>
                    </div>
                  </el-dropdown-item>
                  <el-dropdown-item @click="allFlowchart">
                    <div class="text-0.8rem f-c-s select-none">
                      <div class="mr-2 i-tabler-border-all"></div>
                      <span>所有流程图</span>
                    </div>
                  </el-dropdown-item>
                  <el-dropdown-item @click="publicFlowchart">
                    <div class="text-0.8rem f-c-s select-none">
                      <div class="mr-2 i-tabler-eye-check"></div>
                      <span>公开的流程图</span>
                    </div>
                  </el-dropdown-item>
                  <el-dropdown-item @click="shareFlowchart">
                    <div class="text-0.8rem f-c-s select-none">
                      <div class="mr-2 i-tabler-brand-stackshare"></div>
                      <span>分享的流程图</span>
                    </div>
                  </el-dropdown-item>
                  <el-dropdown-item @click="legalFlowchart">
                    <div class="text-0.8rem f-c-s select-none">
                      <div class="mr-2 i-tabler-lock-square"></div>
                      <span>未审核的流程图</span>
                    </div>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-tooltip>
        </div>
      </div>
    </div>
    <div class="mt-5 text-text-secondary text-0.9rem">文件</div>
    <div class="file-list mt-5 f-c-s flex-wrap flex-gap-1.25rem">
      <File
        v-for="(item, index) in flowchartList"
        :key="item.id"
        :file-image="item.dataUri"
        :file-name="item.fileName"
        :path="'/flowchart/' + item.id"
        @clone="cloneFlowchart(item)"
        @delete="deleteFlowchart(item.id, index)"
        @download="downloadFlowchart(item)"
        @reset="resetFlowchart(index)">
        <template #footer>
          <div class="f-c-s flex-nowrap mt-4 w-100%">
            <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
            <div class="text-0.9rem text-ellipsis line-clamp-1">{{ item.fileName }}</div>
          </div>
          <div class="text-text-secondary text-0.8rem mt-2 f-c-s">
            <div class="i-tabler-clock-edit mr-1"></div>
            {{ formatted("MM-dd HH:mm:ss", item.modifyDate) }}
          </div>
        </template>
      </File>
    </div>
    <el-dialog v-model="dialogVisible" title="修改文件名称" width="30%">
      <el-form :model="flowchartList[clickedIndex]" :rules="fileNameRules">
        <el-form-item prop="fileName">
          <el-input v-model="flowchartList[clickedIndex].fileName" placeholder="请输入文件名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button :disabled="!isFileNameCorrect" type="primary" @click="updateFlowchart">
            <template #icon>
              <div class="i-tabler-check"></div>
            </template>
            确定
          </el-button>
          <el-button @click="dialogVisible = false">
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

<style lang="scss" scoped></style>
