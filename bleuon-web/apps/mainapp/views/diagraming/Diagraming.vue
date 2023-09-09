<script setup lang="ts">
import { dia, initJointJs, showLinkTools, createRect } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const createX = ref(30);
const createY = ref(30);

function addRect() {
  createRect(graph, { x: createX.value, y: createY.value });
  createX.value += 30;
  createY.value += 50;
}

onMounted(() => {
  const jointjs = initJointJs();

  paper = jointjs.paper;
  graph = jointjs.graph;

  paper.on("link:mouseenter", linkView => {
    showLinkTools(linkView);
  });

  paper.on("link:mouseleave", linkView => {
    linkView.removeTools();
  });

  paper.on("element:rect:pointer", () => {
    console.log("e");
  });
});
</script>

<template>
  <div>
    <div class="header bg-amber h-15vh"></div>
    <div class="f-c-b">
      <div class="sidebar relative bg-blue w-15vw h-85vh">
        <el-button @click="addRect">创建 Rect</el-button>
      </div>
      <div id="diagraming"></div>
    </div>
  </div>
</template>

<style lang="scss">
/* port styling */
.available-magnet {
  fill: #5da271;
}

/* element styling */
.available-cell rect {
  stroke-dasharray: 5, 2;
}
</style>
