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
import FlowchartHeaderToolsBottom from "./components/FlowchartHeaderToolsBottom.vue";
import FlowchartHeaderToolsTop from "./components/FlowchartHeaderToolsTop.vue";
import FlowchartFooterTools from "./components/FlowchartFooterTools.vue";
import FlowchartSidebar from "./components/FlowchartSidebar.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();

provide("bleuonPaper", paper);
provide("bleuonGraph", graph);

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

  paper.value.options.defaultConnector = Data.linkConnectorConfig.value;
  paper.value.options.defaultRouter = Data.linkRouterConfig.value;

  paper.value.on({
    "element:pointerclick": view => {
      ListenerService.onElemClick(view);
    },
    "element:mouseover": view => {
      view.model.setPorts(view);
    },
    "element:mouseout": view => {
      view.model.removePorts();
    },
    "element:pointerdblclick": view => {
      ListenerService.onDbClickCell(view, textInputRef.value);
    },
    "link:pointerclick": view => {
      ListenerService.onLinkClick(view);
    },
    "link:pointerdblclick": view => {
      ListenerService.onDbClickCell(view, textInputRef.value);
    },
    "blank:pointerclick": evt => {
      ListenerService.onBlankClick();
    },
    "blank:mousewheel": evt => {
      ListenerService.onBlankMousewheel(evt, paper.value);
    }
  });

  ListenerService.onDelCell();
});
</script>

<template>
  <div class="bleuon__flowchat-container">
    <div
      class="bleuon__flowchat-header border-#dfe2e5 border-b-1 border-b-solid bg-#f6f7f8 h-20vh px-4 py-4">
      <FlowchartHeaderToolsTop class="mb-4" />
      <FlowchartHeaderToolsBottom
        :is-clicked-element="Data.isClickedElement.value"
        :is-clicked-link="Data.isClickedLink.value" />
    </div>
    <div class="bleuon__flowchat-wrapper f-c-b">
      <FlowchartSidebar class="w-15vw h-80vh" />
      <div class="bleuon__flowchat-body relative">
        <div id="bleuon__flowchat-content"></div>
        <FlowchartFooterTools class="w-100% h-5vh" />
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
