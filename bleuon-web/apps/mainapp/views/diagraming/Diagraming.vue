<script setup lang="ts">
import { dia, initJointJs, createPrimaryRectangle } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const createX = ref(30);
const createY = ref(30);

const linkRouter = ref({
  name: "manhattan"
  // args: {
  //   padding: 10
  // }
});
const linkConnector = ref({
  name: "straight",
  args: {
    // cornerType: "line",
    // cornerRadius: 20
  }
});

function addPrimaryRectangle() {
  createPrimaryRectangle(graph, { x: createX.value, y: createY.value });
  createX.value += 30;
  createY.value += 50;
}

onMounted(() => {
  const jointjs = initJointJs();

  paper = jointjs.paper;
  graph = jointjs.graph;

  // 这个属性可以更改连线类型
  paper.options.defaultConnector = linkConnector.value;

  // 这个属性可以更改连线的路由模式
  paper.options.defaultRouter = linkRouter.value;
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
