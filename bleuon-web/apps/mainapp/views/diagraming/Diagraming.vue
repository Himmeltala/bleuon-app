<script setup lang="ts">
import { initJointJs, createPrimaryRectangle } from "@mainapp/lib";
import type { Paper, Graph } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

let paper: Paper = null;
let graph: Graph = null;

const createX = ref(30);
const createY = ref(30);

function addPrimaryRectangle() {
  createPrimaryRectangle(graph, { x: createX.value, y: createY.value });
  createX.value += 30;
  createY.value += 50;
}

onMounted(() => {
  const jointjs = initJointJs();

  paper = jointjs.paper;
  graph = jointjs.graph;
});
</script>

<template>
  <div>
    <div class="header bg-amber h-15vh"></div>
    <div class="f-c-b">
      <div class="sidebar relative bg-blue w-15vw h-85vh">
        <el-button @click="addPrimaryRectangle">基础正方形</el-button>
      </div>
      <div id="diagraming"></div>
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
