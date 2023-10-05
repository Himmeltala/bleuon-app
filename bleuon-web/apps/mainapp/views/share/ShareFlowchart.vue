<script setup lang="ts">
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

async function fetchData() {
  const data = await FlowchartApi.exposeFindOne({ id: route.params.id.toString() }, () => {
    router.back();
  });
  flowchartData.value = data;
}

function regainFromJson() {
  if (flowchartData.value.json) {
    paper.value.freeze();
    graph.value.fromJSON(JSON.parse(flowchartData.value.json));
    paper.value.unfreeze();
  }

  paper.value.options.defaultConnector = JSON.parse(flowchartData.value.connectorDefault);
  paper.value.options.defaultRouter = JSON.parse(flowchartData.value.routerDefault);
}

await fetchData();

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
});
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderToolsTop :type="'share'" class="mb-4" />
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
    }
  }
}
</style>
