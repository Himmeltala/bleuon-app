<script setup lang="ts">
/**
 * @description FlowChat 流程图
 * @author 郑人滏 42020306
 * @since 2023/9/9
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import * as Data from "./data";
import { ListenerService, CellService, CreatorService } from "./service";
import HeaderTools from "./components/HeaderTools.vue";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const textInputRef = ref<HTMLInputElement>();

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchat-content",
    width: "85vw",
    height: "80vh",
    bgColor: "#F3F7F6"
  });

  paper = jointjs.paper;
  graph = jointjs.graph;

  paper.options.defaultConnector = Data.linkConnectorConfig.value;
  paper.options.defaultRouter = Data.linkRouterConfig.value;

  paper.on({
    "element:pointerclick": view => {
      ListenerService.onElemClick(view);
    },
    "element:pointerdblclick": view => {
      CellService.updateInnerText(view, textInputRef.value);
    },
    "link:pointerclick": view => {
      ListenerService.onLinkClick(view);
    },
    "link:pointerdblclick": view => {
      CellService.updateInnerText(view, textInputRef.value);
    },
    "blank:pointerclick": evt => {
      ListenerService.onBlankClick();
    },
    "blank:mousewheel": evt => {
      ListenerService.onBlankMousewheel(evt, paper);
    }
  });

  ListenerService.onDelShape();
});
</script>

<template>
  <div class="bleuon__flowchat-container">
    <div class="bleuon__flowchat-header bg-amber h-20vh">
      <div></div>
      <HeaderTools
        :paper="paper"
        :graph="graph"
        :is-clicked-element="Data.isClickedElement.value"
        :is-clicked-link="Data.isClickedLink.value" />
    </div>
    <div class="bleuon__flowchat-wrapper f-c-b">
      <div class="bleuon__flowchat-sidebar relative bg-blue w-15vw h-80vh">
        <el-button @click="CreatorService.addPrimaryRectangle(graph)">基础正方形</el-button>
      </div>
      <div class="bleuon__flowchat-body relative">
        <div id="bleuon__flowchat-content"></div>
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
