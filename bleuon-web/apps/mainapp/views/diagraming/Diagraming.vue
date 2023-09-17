<script setup lang="ts">
import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import {
  linkConnectorOptions,
  linkRouterOptions,
  linkConnectorConfig,
  linkRouterConfig
} from "./data/config-data";
import { addPrimaryRectangle, watchLinkConnectorConfig, watchLinkRouterConfig } from "./service";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const eleTextInput = ref<HTMLInputElement>();

onMounted(() => {
  const jointjs = initJointJs();

  paper = jointjs.paper;
  graph = jointjs.graph;

  paper.options.defaultConnector = linkConnectorConfig.value;
  paper.options.defaultRouter = linkRouterConfig.value;

  paper.on("cell:pointerdblclick", function (cellView) {
    // @ts-ignore
    const { model } = cellView;
    if (model?.updateText) {
      model.updateText(cellView, eleTextInput);
    }
  });
});

watchLinkConnectorConfig(linkConnectorConfig, paper);
watchLinkRouterConfig(linkRouterConfig, paper);
</script>

<template>
  <div class="bleuon__diagraming-container">
    <div class="bleuon__diagraming-header bg-amber h-15vh">
      <el-select v-model="linkRouterConfig.name" placeholder="请选择路由模式" size="large">
        <el-option
          v-for="item in linkRouterOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value" />
      </el-select>
      <el-select v-model="linkConnectorConfig.name" placeholder="请选择连接端样式" size="large">
        <el-option
          v-for="item in linkConnectorOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value" />
      </el-select>
    </div>
    <div class="bleuon__diagraming-wrapper f-c-b">
      <div class="bleuon__diagraming-sidebar relative bg-blue w-15vw h-85vh">
        <el-button @click="addPrimaryRectangle(graph)">基础正方形</el-button>
      </div>
      <div class="bleu__diagraming-body relative">
        <div id="bleu__diagraming-content"></div>
        <div class="bleuon__diagraming-tools">
          <input ref="eleTextInput" type="text" class="bleuon__diagraming-input absolute hidden" />
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

// .joint-tool[data-tool-name="button"] circle {
//   fill: #333;
// }

// .joint-tool[data-tool-name="connect"]:not(:hover) {
//   opacity: 0;
// }

.active [joint-selector="line"] {
  stroke: #4666e5;
}
</style>
