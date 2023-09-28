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
import * as FLOWCHART_API from "@mainapp/apis/api-flowchart";
import FlowchartHeaderToolsBottom from "./components/FlowchartHeaderToolsBottom.vue";
import FlowchartHeaderToolsTop from "./components/FlowchartHeaderToolsTop.vue";
import FlowchartFooterTools from "./components/FlowchartFooterTools.vue";
import FlowchartSidebar from "./components/FlowchartSidebar.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const route = useRoute();

const config = ref({
  id: "",
  json: "",
  width: 1000,
  height: 1000,
  fileName: "未命名的文件"
});

provide("bleuonPaper", paper);
provide("bleuonGraph", graph);
provide("bleuonConfig", config);

const data = shallowRef(await FLOWCHART_API.queryOne({ id: route.params.id.toString() }));
const textInputRef = shallowRef<HTMLInputElement>();

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchat-content",
    width: "85vw",
    height: "75vh",
    bgColor: "#ffffff"
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  if (data.value.json) {
    graph.value.fromJSON(JSON.parse(data.value.json));
  }

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
      config.value.width = width;
      config.value.height = height;
      config.value.id = route.params.id.toString();
      config.value.json = JSON.stringify(graph.value.toJSON());
      FLOWCHART_API.updateOne(config.value);
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
