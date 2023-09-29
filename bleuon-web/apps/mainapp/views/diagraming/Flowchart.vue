<script setup lang="ts">
/**
 * @description Flowchart 流程图
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import * as Data from "./data";
import { ListenerService } from "./service";
import { FlowchartApi } from "@mainapp/apis";
import FlowchartHeaderToolsBottom from "./components/FlowchartHeaderToolsBottom.vue";
import FlowchartHeaderToolsTop from "./components/FlowchartHeaderToolsTop.vue";
import FlowchartFooterTools from "./components/FlowchartFooterTools.vue";
import FlowchartSidebar from "./components/FlowchartSidebar.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const route = useRoute();

const flowchartData = ref<FlowchartData>({
  width: 1000,
  height: 1000,
  fileName: "未命名的文件"
});

provide(KeyVals.BLEUON_FLOWCHART_PAPER, paper);
provide(KeyVals.BLEUON_FLOWCHART_GRAPH, graph);
provide(KeyVals.BLEUON_FLOWCHART_DATA, flowchartData);

const textInputRef = shallowRef<HTMLInputElement>();

async function fetchData() {
  const data = await FlowchartApi.queryOne({ id: route.params.id.toString() });
  flowchartData.value = data;
}

await fetchData();

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchat-content",
    width: "85vw",
    height: "75vh",
    gridSize: flowchartData.value.gridSize,
    bgColor: flowchartData.value.bgColor,
    drawGrid: {
      name: "doubleMesh",
      args: [
        { color: "#333333", thickness: 1 },
        { color: "gray", scaleFactor: 5, thickness: 5 }
      ]
    }
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  if (flowchartData.value.json) {
    graph.value.fromJSON(JSON.parse(flowchartData.value.json));
  }

  Data.linkConnectorConfig.value = JSON.parse(flowchartData.value.connectorDefault);
  Data.linkRouterConfig.value = JSON.parse(flowchartData.value.routerDefault);

  paper.value.options.defaultConnector = Data.linkConnectorConfig.value;
  paper.value.options.defaultRouter = Data.linkRouterConfig.value;

  paper.value.on({
    "element:pointerclick": view => {
      ListenerService.onPointerClickElement(view);
    },
    "element:pointerdblclick": view => {
      ListenerService.onPointerDbclickElement(view, textInputRef.value);
    },
    "link:pointerclick": view => {
      ListenerService.onPointerClickLink(view);
    },
    "link:pointerdblclick": view => {
      ListenerService.onPointerDbclickElement(view, textInputRef.value);
    },
    "blank:pointerclick": evt => {
      ListenerService.onPointerClickBlank();
    },
    "blank:mousewheel": evt => {
      ListenerService.onMousewheelBlank(evt, paper.value);
    }
  });

  ListenerService.onKeydown({
    ctrlS: () => {
      const { width, height } = paper.value.getArea();
      flowchartData.value.width = width;
      flowchartData.value.height = height;
      flowchartData.value.id = route.params.id.toString();
      flowchartData.value.json = JSON.stringify(graph.value.toJSON());
      flowchartData.value.connectorDefault = JSON.stringify(Data.linkConnectorConfig.value);
      flowchartData.value.routerDefault = JSON.stringify(Data.linkRouterConfig.value);
      FlowchartApi.updateOne(flowchartData.value, () => {});
    }
  });
});
</script>

<template>
  <div class="bleuon__flowchat-container">
    <div
      class="bleuon__flowchat-header h-22vh border-#dfe2e5 border-b-1 border-b-solid bg-#f6f7f8 px-4 py-4">
      <FlowchartHeaderToolsTop class="mb-4" />
      <FlowchartHeaderToolsBottom
        :is-clicked-element="Data.isClickedElement.value"
        :is-clicked-link="Data.isClickedLink.value" />
    </div>
    <div class="bleuon__flowchat-wrapper f-c-b">
      <FlowchartSidebar class="w-15vw h-78vh" />
      <div class="bleuon__flowchat-body relative">
        <div id="bleuon__flowchat-content"></div>
        <FlowchartFooterTools class="w-100% h-3vh" />
        <div class="bleuon__flowchat-extra">
          <input ref="textInputRef" type="text" class="bleuon__flowchat-input absolute hidden" />
        </div>
      </div>
    </div>
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
