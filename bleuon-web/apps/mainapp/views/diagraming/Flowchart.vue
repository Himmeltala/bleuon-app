<script lang="ts" setup>
/**
 * @description Flowchart 流程图
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

// jointjs css
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

import { throttle } from "@common/utils/prevent";
import { shortcuts } from "@common/data/el-components";
import { commitForm } from "@common/utils/form-validators";
import { dia, initJointJs } from "@mainapp/lib";
import { getDataUri } from "@mainapp/lib/tools";
import { FlowchartApi } from "@mainapp/apis";
import * as Data from "@mainapp/data/diagraming/flowchart";
import { ListenerService } from "@mainapp/service/diagraming/flowchart";

// components
import HeaderToolsBottom from "@mainapp/components/diagraming/flowchart/HeaderToolsBottom.vue";
import HeaderToolsTop from "@mainapp/components/diagraming/flowchart/HeaderToolsTop.vue";
import FooterTools from "@mainapp/components/diagraming/flowchart/FooterTools.vue";
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
  flowchartData.value = await FlowchartApi.findOne({ id: route.params.id.toString() });
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
  FlowchartApi.updateOne(flowchartData.value, () => {});
}

const updateThrottle = throttle(updateFlowchartData, 3000);

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
  graph.value.on("add", updateThrottle);
  // @ts-ignore
  graph.value.on("remove", updateThrottle);

  ListenerService.onKeydown({
    ctrlS: updateThrottle
  });
});

const dialogVisible = ref(false);
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
  commitForm(shareFormRef.value, () => {
    flowchartData.value.isShare = 1;
    FlowchartApi.updateOne(flowchartData.value, data => {
      if (data.code === 200) {
        ElMessage.success("分享成功，复制链接浏览！");
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
  FlowchartApi.updateOne(flowchartData.value, data => {
    if (data.code === 200) {
      ElMessage.success("取消分享成功！");
    } else {
      flowchartData.value.isShare = 1;
      ElMessage.error("取消分享失败！");
    }
  });
}

await fetchData();
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderToolsTop :data="flowchartData" class="mb-4" @change="updateThrottle">
        <template #tools>
          <el-tooltip content="分享">
            <div class="hover i-tabler-share" @click="dialogVisible = !dialogVisible"></div>
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
    <el-dialog v-model="dialogVisible" title="分享流程图" width="40%">
      <el-form
        ref="shareFormRef"
        :model="flowchartData"
        :rules="shareFormRules"
        label-position="right"
        label-width="100px">
        <el-form-item label="状态">
          <div v-if="!flowchartData.isShare">流程图没有公开，点击创建链接</div>
          <div v-else>流程图已经公开，点击链接浏览</div>
        </el-form-item>
        <el-form-item v-if="flowchartData.isShare" label="链接地址">
          <el-link type="primary" @click="$router.push('/share/flowchart/' + flowchartData.id)">
            http://localhost:5173/#/share/flowchart/{{ flowchartData.id }}
          </el-link>
        </el-form-item>
        <el-form-item label="截止日期" prop="deadShareDate">
          <el-date-picker
            v-model="flowchartData.deadShareDate"
            :shortcuts="shortcuts"
            type="datetime" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
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
