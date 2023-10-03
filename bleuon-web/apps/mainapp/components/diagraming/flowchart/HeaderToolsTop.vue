<script setup lang="ts">
import { dia } from "@mainapp/lib";
import { convertSvgToImage } from "@mainapp/lib/tools";
import MenuAvatar from "@mainapp/components/MenuAvatar.vue";
import { formatted } from "@common/utils/date";
import { shortcuts } from "@common/data/el-components";
import { FlowchartApi } from "@mainapp/apis";

import type { FormInstance, FormRules } from "element-plus";

const paper = inject<Ref<dia.Paper>>(KeyVals.BLEUON_FLOWCHART_PAPER);
const graph = inject<Ref<dia.Graph>>(KeyVals.BLEUON_FLOWCHART_GRAPH);
const flowchartData = inject<Ref<FlowchartData>>(KeyVals.BLEUON_FLOWCHART_DATA);
const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);

const editFile = ref(false);

const props = defineProps({
  data: {
    type: Object
  },
  type: {
    type: String as PropType<"share" | "privacy">,
    default: "privacy"
  }
});
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
    () => {
      ElMessage.success("下载成功！");
    },
    () => {
      ElMessage.error("下载失败！");
    }
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

function confirmChangeFileName() {
  emits("change");
  editFile.value = !editFile.value;
}

const dialogVisible = ref(false);
const formRef = ref<FormInstance>();
const formRules = reactive<FormRules<any>>({
  deadShareDate: [
    {
      type: "date",
      required: true,
      message: "请选择一个截止日期",
      trigger: "change"
    },
    {
      type: "date",
      required: true,
      message: "请选择一个截止日期",
      trigger: "blur"
    }
  ]
});

async function confirmShare() {
  if (props.type === "share") {
    // 直接粘贴链接
  } else {
    if (!formRef.value) return;
    await formRef.value.validate((valid, fields) => {
      if (valid) {
        flowchartData.value.isShare = 1;
        FlowchartApi.updateOne(flowchartData.value, data => {
          if (data.code === 200) {
            ElMessage.success("分享成功，复制链接浏览！");
          } else {
            flowchartData.value.isShare = 0;
            ElMessage.error("分享失败！");
          }
        });
      }
    });
  }
}

function cancelShare() {
  flowchartData.value.isShare = 0;
  flowchartData.value.deadShareDate = null;
  FlowchartApi.updateOne(flowchartData.value, data => {
    if (data.code === 200) {
      ElMessage.success("取消分享成功！");
    } else {
      flowchartData.value.isShare = 1;
      ElMessage.error("取消分享失败！");
    }
  });
}

function importFlowchart() {
  flowchartData.value.fileName = "导入模板_" + flowchartData.value.fileName;
  FlowchartApi.cloneOne(flowchartData.value, () => {
    ElMessage.success("导入成功，回到自己工作台查看吧！");
  });
}
</script>

<template>
  <div class="b-border-primary b-b-1 b-b-solid f-c-b pb-4">
    <div class="left f-c-s">
      <el-button @click="$router.back()" type="primary" bg text>
        <template #icon>
          <div class="i-tabler-chevron-left"></div>
        </template>
      </el-button>
      <img
        @click="$router.push('/workbench')"
        src="/bleuon-icon.png"
        class="mr-4 w-30 h-15 object-cover cursor-pointer" />
      <div>
        <div v-show="editFile" @keyup.enter="confirmChangeFileName">
          <el-input v-model="calcFileName" size="small" placeholder="请输入文件名" />
        </div>
        <div class="text-gray-700" v-show="!editFile" @click="editFile = !editFile">
          {{ calcFileName }}
        </div>
        <div class="mt-2">
          <div class="text-gray-500 text-0.8rem f-c-c">
            <div class="i-tabler-clock mr-1"></div>
            上次更新：{{ formatted("yyyy-MM-dd HH:mm:ss", new Date(flowchartData.modifyDate)) }}
          </div>
        </div>
      </div>
    </div>
    <div class="right f-c-s">
      <div class="mr-6 f-c-s text-gray-700 text-1.5rem">
        <el-tooltip content="下载">
          <div @click="download" class="hover mr-4 i-tabler-download"></div>
        </el-tooltip>
        <el-tooltip content="分享">
          <div @click="dialogVisible = !dialogVisible" class="hover i-tabler-share"></div>
        </el-tooltip>
        <!-- 用户没有登陆 -->
        <el-tooltip content="导入模板" v-if="token">
          <div
            v-if="type === 'share'"
            @click="importFlowchart"
            class="hover i-tabler-file-import ml-4"></div>
        </el-tooltip>
      </div>
      <MenuAvatar v-if="token" />
    </div>
    <el-dialog v-model="dialogVisible" title="分享流程图" width="40%">
      <el-form
        ref="formRef"
        :rules="formRules"
        :model="flowchartData"
        label-width="100px"
        label-position="right">
        <el-form-item label="状态">
          <div v-if="!flowchartData.isShare">流程图没有公开，点击创建链接</div>
          <div v-else>流程图已经公开，点击链接浏览</div>
        </el-form-item>
        <el-form-item v-if="flowchartData.isShare" label="链接地址">
          <el-link type="primary" @click="$router.push('/share/flowchart/' + flowchartData.id)">
            http://localhost:5173/#/share/flowchart/{{ flowchartData.id }}
          </el-link>
        </el-form-item>
        <el-form-item prop="deadShareDate" label="截止日期">
          <el-date-picker
            :disabled="type === 'share'"
            v-model="flowchartData.deadShareDate"
            type="datetime"
            :shortcuts="shortcuts" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span v-if="type === 'privacy'" class="dialog-footer">
          <el-button :disabled="!!flowchartData.isShare" type="primary" @click="confirmShare">
            <template #icon>
              <div class="i-tabler-check"></div>
            </template>
            确定公开
          </el-button>
          <el-button :disabled="!flowchartData.isShare" @click="cancelShare">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消公开
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
