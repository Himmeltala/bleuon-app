<script setup lang="ts">
import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import * as Data from "./data";
import * as Service from "./service";
// components
import HeaderTools from "./HeaderTools.vue";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const textInputElement = ref<HTMLInputElement>();

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleu__diagraming-content",
    width: "85vw",
    height: "80vh",
    backgroundColor: "#F3F7F6"
  });

  paper = jointjs.paper;
  graph = jointjs.graph;

  paper.options.defaultConnector = Data.linkConnectorConfig.value;
  paper.options.defaultRouter = Data.linkRouterConfig.value;

  paper.on({
    "element:mouseenter": view => {},
    "element:mouseleave": view => {},
    "element:pointerclick": view => {
      Data.clickedCurrView.value = view;
      Service.insShapeTools(view, Data.clickedLastView);
    },
    "element:pointerdblclick": view => {
      Service.updateShapeText(view, textInputElement.value);
    },
    "blank:pointerclick": view => {
      Service.uniShapeTools(Data.clickedLastView);
    },
    "link:mouseenter": view => {
      Service.insLinkTools(view);
    },
    "link:mouseleave": view => {
      Service.uniLinkTools(view);
    }
  });
});
</script>

<template>
  <div class="bleuon__diagraming-container">
    <div class="bleuon__diagraming-header bg-amber h-20vh">
      <div></div>
      <HeaderTools :paper="paper" :graph="graph" />
    </div>
    <div class="bleuon__diagraming-wrapper f-c-b">
      <div class="bleuon__diagraming-sidebar relative bg-blue w-15vw h-80vh">
        <el-button @click="Service.addPrimaryRectangle(graph)">基础正方形</el-button>
      </div>
      <div class="bleu__diagraming-body relative">
        <div id="bleu__diagraming-content"></div>
        <div class="bleuon__diagraming-tools">
          <input
            ref="textInputElement"
            type="text"
            class="bleuon__diagraming-input absolute hidden" />
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
.joint-element {
  .joint-port {
    circle {
      --uno: transition-all-300;
      fill: transparent;
    }
  }

  &:hover {
    .joint-port {
      circle {
        fill: black !important;
      }
    }
  }
}

.active [joint-selector="line"] {
  stroke: #4666e5;
}
</style>
