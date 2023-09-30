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
import { downloadWithDataUri } from "@mainapp/lib/tools";
import { FlowchartApi } from "@mainapp/apis";

const flowchartData = shallowRef<FlowchartData>({});
const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const route = useRoute();

async function fetchData() {
  const data = await FlowchartApi.exposeQueryOne({ id: route.params.id.toString() });
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
    height: "85vh",
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
    "blank:mousewheel": evt => {
      ListenerService.onMousewheelBlank(evt, paper.value);
    }
  });

  paper.value.setInteractivity({
    elementMove: false,
    elementResize: false,
    linkMove: false,
    linkVertexMove: false
  });
});
</script>

<template>
  <div class="bleuon__flowchart-wrapper">
    <div class="bleuon__flochart-header h-15vh">
      <!--  -->
    </div>
    <div class="bleuon__flowchart-body h-85vh w-100vw">
      <div id="bleuon__flowchart-content"></div>
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
