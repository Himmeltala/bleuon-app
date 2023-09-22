<script setup lang="ts">
import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import * as Data from "./data";
import * as Service from "./service";
import HeaderTools from "./components/HeaderTools.vue";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const isClickedLink = ref(false);
const isClickedElement = ref(false);
const textInputRef = ref<HTMLInputElement>();

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
    "element:pointerclick": view => {
      isClickedElement.value = true;
      isClickedLink.value = false;
      Data.clickedCurrView.value = view;
      Service.installShapeTools(view, Data.clickedLastView);
    },
    "element:pointerdblclick": view => {
      Service.updateShapeText(view, textInputRef.value);
    },
    "blank:pointerclick": view => {
      isClickedElement.value = false;
      isClickedLink.value = false;
      Service.uninstallShapeTools(Data.clickedLastView);
      Service.uninstallLinkTools(Data.clickedLastView);
    },
    "link:pointerclick": view => {
      isClickedLink.value = true;
      isClickedElement.value = false;
      Data.clickedCurrView.value = view;
      Service.installLinkTools(view, Data.clickedLastView);
    },
    "link:pointerdblclick": view => {
      Service.updateShapeText(view, textInputRef.value);
    }
  });
});
</script>

<template>
  <div class="bleuon__diagraming-container">
    <div class="bleuon__diagraming-header bg-amber h-20vh">
      <div></div>
      <HeaderTools
        :paper="paper"
        :graph="graph"
        :clicked-element="isClickedElement"
        :clicked-link="isClickedLink" />
    </div>
    <div class="bleuon__diagraming-wrapper f-c-b">
      <div class="bleuon__diagraming-sidebar relative bg-blue w-15vw h-80vh">
        <el-button @click="Service.addPrimaryRectangle(graph)">基础正方形</el-button>
      </div>
      <div class="bleu__diagraming-body relative">
        <div id="bleu__diagraming-content"></div>
        <div class="bleuon__diagraming-extra">
          <input ref="textInputRef" type="text" class="bleuon__diagraming-input absolute hidden" />
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
