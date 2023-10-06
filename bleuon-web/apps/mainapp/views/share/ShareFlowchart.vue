<script lang="ts" setup>
/**
 * @description 分享的 Flowchart 流程图
 * @author 郑人滏 42020306
 * @since 2023/9/30
 * @link https://github.com/himmelbleu/bleuon-app
 */

// jointjs css
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

import { dia, initJointJs } from "@mainapp/lib";
import { FlowchartApi } from "@mainapp/apis";
import { ListenerService } from "@mainapp/service/diagraming/flowchart";
import * as Data from "@mainapp/data/diagraming/flowchart";
import { formatted } from "@common/utils/date";

// components
import HeaderToolsBottom from "@mainapp/components/diagraming/flowchart/HeaderToolsBottom.vue";
import HeaderToolsTop from "@mainapp/components/diagraming/flowchart/HeaderToolsTop.vue";
import FooterTools from "@mainapp/components/diagraming/flowchart/FooterTools.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const route = useRoute();
const router = useRouter();

const flowchartData = ref<FlowchartData>({});
const textInputRef = shallowRef<HTMLInputElement>();

provide(KeyVals.BLEUON_FLOWCHART_PAPER, paper);
provide(KeyVals.BLEUON_FLOWCHART_GRAPH, graph);
provide(KeyVals.BLEUON_FLOWCHART_DATA, flowchartData);
const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);

async function fetchData() {
  const data = await FlowchartApi.exposeFindOne({ id: route.params.id.toString() }, () => {
    router.back();
  });
  flowchartData.value = data;
}

function regainFromJson() {
  paper.value.freeze();

  if (flowchartData.value.json) {
    graph.value.fromJSON(JSON.parse(flowchartData.value.json));
  }

  paper.value.options.defaultConnector = JSON.parse(flowchartData.value.connectorDefault);
  paper.value.options.defaultRouter = JSON.parse(flowchartData.value.routerDefault);

  paper.value.unfreeze();
}

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchart-content",
    width: "100vw",
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
});

const dialogVisible = ref(false);

function importFlowchart() {
  flowchartData.value.fileName = "模板_" + flowchartData.value.fileName;
  FlowchartApi.cloneOne(flowchartData.value, res => ElMessage.success(res.message));
}

await fetchData();
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderToolsTop :data="flowchartData" class="mb-4">
        <template #tools>
          <el-tooltip content="分享">
            <div class="hover i-tabler-share mr-4" @click="dialogVisible = !dialogVisible"></div>
          </el-tooltip>
          <el-tooltip v-if="token" content="导入模板">
            <div>
              <div class="hover i-tabler-file-import" @click="importFlowchart"></div>
            </div>
          </el-tooltip>
        </template>
      </HeaderToolsTop>
      <HeaderToolsBottom
        :is-clicked-element="Data.isClickedElement.value"
        :is-clicked-link="Data.isClickedLink.value" />
    </div>
    <div class="bleuon__flowchart-wrapper f-c-b">
      <div class="left"></div>
      <div class="right">
        <div id="bleuon__flowchart-content"></div>
        <FooterTools class="h-3vh" />
      </div>
      <div class="bleuon__flowchart-extra">
        <input ref="textInputRef" class="bleuon__flowchart-input absolute hidden" type="text" />
      </div>
    </div>
    <el-dialog v-model="dialogVisible" title="分享流程图" width="40%">
      <el-form ref="shareFormRef" label-position="right" label-width="100px">
        <el-form-item label="状态">流程图已经公开，点击链接浏览</el-form-item>
        <el-form-item label="链接地址">
          <el-link type="primary" @click="$router.push('/share/flowchart/' + flowchartData.id)">
            http://localhost:5173/#/share/flowchart/{{ flowchartData.id }}
          </el-link>
        </el-form-item>
        <el-form-item label="截止日期">
          {{ formatted("yyyy-MM-dd HH:mm:ss", flowchartData.deadShareDate) }}
        </el-form-item>
      </el-form>
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
    }
  }
}
</style>
