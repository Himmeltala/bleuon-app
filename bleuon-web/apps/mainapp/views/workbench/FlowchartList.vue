<script lang="ts" setup>
/**
 * @description flowcharts 流程图列表
 * @author zheng
 * @since 2023/8/23
 */

import { downloadWithDataURI } from "@common/lib/jointjs/utils";
import { DateUtil, ElFormUtil } from "@common/utils";
import { Requests } from "@common/requests";
import File from "@mainapp/components/File.vue";
import WorkbenchHeader from "@mainapp/fragments/workbench/WorkbenchHeader.vue";

const clickedIndex = ref(0);
const mainData = ref<FlowchartModel[]>([]);
const dialogVisible = ref(false);
const isFileNameCorrect = ref(false);
const fileNameRules = reactive({
  filename: [
    { validator: ElFormUtil.filenameValidator(isFileNameCorrect), trigger: "blur" },
    { validator: ElFormUtil.filenameValidator(isFileNameCorrect), trigger: "change" }
  ]
});

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

async function fetchData(params: any) {
  mainData.value = await Requests.Flowchart.findAllByCriteria({
    ...params,
    collectorId: token.id
  });
}

function upgrade() {
  Requests.Flowchart.upgrade({ model: mainData.value[clickedIndex.value] }, () => {
    dialogVisible.value = !dialogVisible.value;
  });
}

function rename(index: number) {
  dialogVisible.value = !dialogVisible.value;
  clickedIndex.value = index;
}

function download(data: FlowchartModel) {
  downloadWithDataURI(data.dataUri, data.filename, "jpeg");
}

function replicate(data: FlowchartModel) {
  Requests.Flowchart.replicate({ ...data, consumerId: token.id }, async () => {
    await fetchData({});
  });
}

function remove(id: string, index: number) {
  Requests.Flowchart.deleteById({ id }, async () => {
    mainData.value.splice(index, 1);
  });
}

const searchVal = ref("");
const isModifyDateAsc = ref(false);
const isCreateDateAsc = ref(false);

function allFlowchart() {
  fetchData({
    filename: searchVal.value
  });
}

function publicFlowchart() {
  fetchData({
    filename: searchVal.value,
    isPublic: 1
  });
}

function shareFlowchart() {
  fetchData({
    filename: searchVal.value,
    isShare: 1
  });
}

function legalFlowchart() {
  fetchData({
    filename: searchVal.value,
    isLegal: 1
  });
}

function modifyDateFlowchart() {
  isModifyDateAsc.value = !isModifyDateAsc.value;
  fetchData({ sequences: [{ col: "modify_date", isAsc: isModifyDateAsc.value }] });
}

function createDateFlowchart() {
  isCreateDateAsc.value = !isCreateDateAsc.value;
  fetchData({ sequences: [{ col: "create_date", isAsc: isCreateDateAsc.value }] });
}

async function search() {
  fetchData({ filename: searchVal.value });
}

await fetchData({});
</script>

<template>
  <div class="flowchart-list max-h-100vh">
    <WorkbenchHeader v-model:value="searchVal" @enter-search="search"></WorkbenchHeader>
    <div class="px-10 pb-10">
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
      <div class="mt-5 text-text-regular text-0.9rem">文件</div>
      <div class="file-list mt-5 f-s-s flex-wrap flex-gap-1.25rem">
        <File
          v-for="(item, index) in mainData"
          :key="item.id"
          :file-image="item.dataUri"
          :path="'/flowchart/' + item.id"
          :rename="() => rename(index)"
          :download="() => download(item)"
          :replicate="() => replicate(item)"
          :remove="() => remove(item.id, index)">
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
              <el-tag
                v-if="!item.isPublic && !item.isBlueprint && !item.isShare"
                type="info"
                size="small">
                私密
              </el-tag>
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
      </div>
      <el-dialog v-model="dialogVisible" title="修改文件名称" width="30%">
        <el-form :model="mainData[clickedIndex]" :rules="fileNameRules">
          <el-form-item prop="filename">
            <el-input
              v-model="mainData[clickedIndex].filename"
              placeholder="请输入文件名称"
              @keyup.enter="upgrade" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button :disabled="!isFileNameCorrect" type="primary" @click="upgrade">
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
  </div>
</template>

<style lang="scss" scoped></style>
