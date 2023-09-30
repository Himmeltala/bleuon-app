<script setup lang="ts">
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
  const data = await FlowchartApi.queryOne({ id: route.params.id.toString() });
  flowchartData.value = data;
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
  if (flowchartData.value.json) {
    paper.value.freeze();
    graph.value.fromJSON(JSON.parse(flowchartData.value.json));
    paper.value.unfreeze();
  }

  Data.linkConnectorConfig.value = JSON.parse(flowchartData.value.connectorDefault);
  Data.linkRouterConfig.value = JSON.parse(flowchartData.value.routerDefault);

  paper.value.options.defaultConnector = Data.linkConnectorConfig.value;
  paper.value.options.defaultRouter = Data.linkRouterConfig.value;
}

await fetchData();

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchart-content",
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

  regainFromJson();

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
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-#f6f7f8 px-4 py-4">
      <HeaderToolsTop @change="updateThrottle" class="mb-4" />
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
        <input ref="textInputRef" type="text" class="bleuon__flowchart-input absolute hidden" />
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
