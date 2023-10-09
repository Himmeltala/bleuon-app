<script lang="ts" setup>
/**
 * @description Flowchart 流程图
 * @author zheng
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

// jointjs css
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

import { FlowchartApi } from "@mainapp/apis";
import * as Data from "@mainapp/data/diagraming/flowchart";
import { dia, initJointJs } from "@mainapp/lib";
import { getDataUri } from "@mainapp/lib/tools";
import { ListenerService } from "@mainapp/service/diagraming/flowchart";

import { ElDatePickerData, ElSelectData } from "@common/data";
import { FormValidatorsUtil, PreventUtil } from "@common/utils";

// components
import FooterTools from "@mainapp/components/diagraming/flowchart/FooterTools.vue";
import HeaderToolsBottom from "@mainapp/components/diagraming/flowchart/HeaderToolsBottom.vue";
import HeaderToolsTop from "@mainapp/components/diagraming/flowchart/HeaderToolsTop.vue";
import Sidebar from "@mainapp/components/diagraming/flowchart/Sidebar.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const route = useRoute();

const flowchartData = ref<FlowchartData>({});
const textInputRef = shallowRef<HTMLInputElement>();

provide(KeyVals.BLEUON_FLOWCHART_PAPER, paper);
provide(KeyVals.BLEUON_FLOWCHART_GRAPH, graph);
provide(KeyVals.BLEUON_FLOWCHART_DATA, flowchartData);

async function fetchData() {
  flowchartData.value = await FlowchartApi.findById({ id: route.params.id.toString() });
}

function updateFlowchartData() {
  const { width, height } = paper.value.getArea();
  flowchartData.value.width = width;
  flowchartData.value.height = height;
  flowchartData.value.id = route.params.id.toString();
  flowchartData.value.json = JSON.stringify(graph.value.toJSON());
  flowchartData.value.connectorDefault = JSON.stringify(Data.linkConnectorConfig.value);
  flowchartData.value.routerDefault = JSON.stringify(Data.linkRouterConfig.value);
  flowchartData.value.dataUri = getDataUri(paper.value, graph.value);
  FlowchartApi.renewal(flowchartData.value, () => {
  });
}

const updateThrottle = PreventUtil.throttle(updateFlowchartData, 3000);

function regainFromJson() {
  paper.value.freeze();

  if (flowchartData.value.json) {
    graph.value.fromJSON(JSON.parse(flowchartData.value.json));
  }

  Data.linkConnectorConfig.value = JSON.parse(flowchartData.value.connectorDefault);
  Data.linkRouterConfig.value = JSON.parse(flowchartData.value.routerDefault);

  paper.value.options.defaultConnector = Data.linkConnectorConfig.value;
  paper.value.options.defaultRouter = Data.linkRouterConfig.value;

  paper.value.unfreeze();
}

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchart-content",
    width: "85vw",
    height: "75vh",
    gridSize: flowchartData.value.gridSize,
    bgColor: flowchartData.value.bgColor,
    drawGrid: JSON.parse(flowchartData.value.drawGrid)
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  regainFromJson();

  paper.value.on({
    "element:pointerclick": view => ListenerService.onPointerClickElement(view),
    "element:pointerdblclick": view =>
      ListenerService.onPointerDbclickElement(view, textInputRef.value),
    "link:pointerclick": view => ListenerService.onPointerClickLink(view),
    "link:pointerdblclick": view =>
      ListenerService.onPointerDbclickElement(view, textInputRef.value),
    "blank:pointerclick": () => ListenerService.onPointerClickBlank(),
    "blank:mousewheel": evt => ListenerService.onMousewheelBlank(evt, paper.value)
  });

  // @ts-ignore
  graph.value.on("change", updateThrottle);
  // @ts-ignore
  graph.value.on("add", updateFlowchartData);
  // @ts-ignore
  graph.value.on("remove", updateFlowchartData);

  ListenerService.onKeydown({
    ctrlS: updateThrottle
  });
});

const shareDialogVisible = ref(false);
const shareFormRef = ref<FormInstance>();
const shareFormRules = reactive<FormRules<any>>({
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

function confirmShare() {
  FormValidatorsUtil.validate(shareFormRef.value, () => {
    flowchartData.value.isShare = 1;
    FlowchartApi.renewal(flowchartData.value, res => {
      if (res.code === 200) {
        ElMessage.success("分享成功！");
      } else {
        flowchartData.value.isShare = 0;
        ElMessage.error("分享失败！");
      }
    });
  });
}

function cancelShare() {
  flowchartData.value.isShare = 0;
  flowchartData.value.deadShareDate = null;
  FlowchartApi.renewal(flowchartData.value, res => {
    if (res.code === 200) {
      ElMessage.success("取消分享成功！");
    } else {
      flowchartData.value.isShare = 1;
      ElMessage.error("取消分享失败！");
    }
  });
}

const releaseDialogVisible = ref(false);
const releaseFormRef = ref<FormInstance>();
const releaseFormData = reactive({
  description: "",
  scene: "全部",
  price: "免费",
  tags: ""
});
const releaseFormRules = reactive<FormRules<any>>({
  description: [
    {
      required: true,
      message: "请输入文件描述",
      trigger: "blur"
    }
  ],
  scene: [
    {
      required: true,
      message: "请选择一个适用场景",
      trigger: "blur"
    }
  ],
  price: [
    {
      required: true,
      message: "请选择一个收费标准",
      trigger: "blur"
    }
  ]
});

const releaseTagVal = ref("");
const releaseTagList = ref([]);

function onEnterReleaseTag() {
  if (releaseTagVal.value.length < 3) {
    ElMessage.warning("标签内容不能少于3个字符");
    return;
  }

  if (releaseTagVal.value.length > 10) {
    ElMessage.warning("标签内容不能超过10个字符");
    return;
  }

  if (releaseTagList.value.length < 5) {
    if (!releaseTagList.value.includes(releaseTagVal.value)) {
      releaseTagList.value.push(releaseTagVal.value);
    } else {
      ElMessage.warning("不能重复添加标签");
    }
  } else {
    ElMessage.warning("标签最多添加5个");
  }
}

function closeReleaseTag(index: number) {
  releaseTagList.value.splice(index, 1);
}

function confirmRelease() {
  FormValidatorsUtil.validate(releaseFormRef.value, () => {
    releaseFormData.tags = JSON.stringify(releaseTagList.value);
    FlowchartApi.release({ ...{ flowchartId: flowchartData.value.id }, ...releaseFormData }, () => {
      flowchartData.value.isPublic = 1;
    });
  });
}

function cancelRelease() {
  FlowchartApi.cancelRelease({ flowchartId: flowchartData.value.id }, () => {
    flowchartData.value.isPublic = 0;
  });
}

await fetchData();
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderToolsTop :data="flowchartData" class="mb-4" @change="updateFlowchartData">
        <template #tools>
          <el-tooltip content="分享">
            <div
              class="hover i-tabler-share mr-4"
              @click="shareDialogVisible = !shareDialogVisible"></div>
          </el-tooltip>
          <el-tooltip content="发布和公开">
            <div
              class="hover i-tabler-location-share"
              @click="releaseDialogVisible = !releaseDialogVisible"></div>
          </el-tooltip>
        </template>
      </HeaderToolsTop>
      <HeaderToolsBottom
        :is-clicked-element="Data.isClickedElement.value"
        :is-clicked-link="Data.isClickedLink.value" />
    </div>
    <div class="bleuon__flowchart-wrapper f-c-b">
      <div class="left">
        <Sidebar class="h-78vh" />
      </div>
      <div class="right">
        <div id="bleuon__flowchart-content"></div>
        <FooterTools class="h-3vh" />
      </div>
      <div class="bleuon__flowchart-extra">
        <input ref="textInputRef" class="bleuon__flowchart-input absolute hidden" type="text" />
      </div>
    </div>
    <el-dialog v-model="shareDialogVisible" title="分享流程图" width="40%">
      <el-form
        ref="shareFormRef"
        :model="flowchartData"
        :rules="shareFormRules"
        label-position="right"
        label-width="100px">
        <el-form-item label="状态">
          <div v-if="!flowchartData.isShare">流程图没有分享，请创建分享链接</div>
          <div v-else>流程图已经分享，点击链接浏览</div>
        </el-form-item>
        <el-form-item v-if="flowchartData.isShare" label="链接地址">
          <router-link :to="'/share/flowchart/' + flowchartData.id">
            <el-link type="primary">
              http://localhost:5173/#/share/flowchart/{{ flowchartData.id }}
            </el-link>
          </router-link>
        </el-form-item>
        <el-form-item label="截止日期" prop="deadShareDate">
          <el-date-picker
            v-model="flowchartData.deadShareDate"
            :shortcuts="ElDatePickerData.futureShortcuts"
            type="datetime" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span>
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
    <el-dialog v-model="releaseDialogVisible" title="发布和公开流程图" width="40%">
      <el-form
        v-if="!flowchartData.isPublic"
        ref="releaseFormRef"
        :model="releaseFormData"
        :rules="releaseFormRules"
        label-position="left"
        label-width="90px">
        <el-form-item label="文件描述" prop="description">
          <el-input v-model="releaseFormData.description" />
        </el-form-item>
        <el-form-item label="场景" prop="scene">
          <el-select v-model="releaseFormData.scene" placeholder="请输入适用场景">
            <el-option
              v-for="item in ElSelectData.sceneList"
              :key="item.label"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-select v-model="releaseFormData.price" placeholder="请输入收费标准">
            <el-option
              v-for="item in ElSelectData.priceList"
              :key="item.label"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-input
            v-model="releaseTagVal"
            placeholder="按下回车键添加标签，最多添加5个"
            @keyup.enter="onEnterReleaseTag" />
          <div class="f-c-s mt-2">
            <el-tag
              v-for="(item, index) in releaseTagList"
              :key="item"
              class="mr-2"
              closable
              @close="closeReleaseTag(index)">
              {{ item }}
            </el-tag>
          </div>
        </el-form-item>
      </el-form>
      <div v-else class="text-center text-1.2rem font-bold">
        已经发布到社区模板，若需要你可以取消发布
      </div>
      <template #footer>
        <span>
          <el-button
            v-if="!flowchartData.isPublic"
            @click="releaseDialogVisible = !releaseDialogVisible">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消
          </el-button>
          <el-button v-else type="danger" @click="cancelRelease">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消发布
          </el-button>
          <el-button :disabled="!!flowchartData.isPublic" type="primary" @click="confirmRelease">
            <template #icon>
              <div class="i-tabler-check"></div>
            </template>
            <span v-if="!flowchartData.isPublic">确定发布</span>
            <span v-else>已经发布</span>
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style lang="scss">
.joint-element {
  .joint-port {
    circle {
      fill: transparent;
      stroke: transparent;
      stroke-width: 0;
      stroke-dasharray: none;
      --uno: transition-all-300;
    }
  }

  &:hover {
    .joint-port {
      circle {
        fill: white !important;
        stroke: black;
        stroke-width: 1px;
        stroke-dasharray: none;
      }
    }
  }
}

.active [joint-selector="line"] {
  stroke: #4666e5;
}
</style>
