<script setup lang="ts">
import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import * as DATA from "./data";
import * as SERVICE from "./service";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const textInputElement = ref<HTMLInputElement>();

onMounted(() => {
  const jointjs = initJointJs();

  paper = jointjs.paper;
  graph = jointjs.graph;

  paper.options.defaultConnector = DATA.linkConnectorConfig.value;
  paper.options.defaultRouter = DATA.linkRouterConfig.value;

  paper.on({
    "element:mouseenter": view => {},
    "element:mouseleave": view => {},
    "element:pointerclick": view => {
      DATA.clickedCurrView.value = view;
      SERVICE.insShapeTools(view, DATA.clickedLastView);
    },
    "element:pointerdblclick": view => {
      SERVICE.updateShapeText(view, textInputElement.value);
    },
    "blank:pointerclick": view => {
      SERVICE.uniShapeTools(DATA.clickedLastView);
    },
    "link:mouseenter": view => {
      SERVICE.insLinkTools(view);
    },
    "link:mouseleave": view => {
      SERVICE.uniLinkTools(view);
    }
  });
});
</script>

<template>
  <div class="bleuon__diagraming-container">
    <div class="bleuon__diagraming-header bg-amber h-15vh">
      <div></div>
      <div class="f-c-s">
        <div class="f-c-c">
          <div>
            <el-tooltip content="字体" placement="bottom">
              <el-select
                @change="value => SERVICE.changeTextFamily(DATA.clickedCurrView.value, value)"
                v-model="DATA.textStyle.value"
                placeholder="请选择字体">
                <el-option
                  v-for="item in DATA.fontFamily"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-tooltip>
          </div>
          <div class="ml-2">
            <el-tooltip content="加粗" placement="bottom">
              <el-button text bg @click="SERVICE.changeTextBold(DATA.clickedCurrView.value)">
                <template #icon>
                  <div class="i-tabler-bold"></div>
                </template>
              </el-button>
            </el-tooltip>
          </div>
          <div class="ml-2">
            <el-tooltip content="斜体" placement="bottom">
              <el-button text bg @click="SERVICE.changeTextItalic(DATA.clickedCurrView.value)">
                <template #icon>
                  <div class="i-tabler-italic"></div>
                </template>
              </el-button>
            </el-tooltip>
          </div>
          <div class="ml-2">
            <el-tooltip content="下划线" placement="bottom">
              <el-button text bg @click="SERVICE.changeTextUnderline(DATA.clickedCurrView.value)">
                <template #icon>
                  <div class="i-tabler-underline"></div>
                </template>
              </el-button>
            </el-tooltip>
          </div>
          <div class="ml-2">
            <el-color-picker
              @change="value => SERVICE.changeTextColor(DATA.clickedCurrView.value, value)"
              v-model="DATA.textColor.value" />
          </div>
          <div class="ml-2">
            <el-tooltip content="字号(px)" placement="bottom">
              <el-input-number
                v-model="DATA.textSize.value"
                :min="14"
                :max="30"
                controls-position="right"
                @change="value => SERVICE.changeTextSize(DATA.clickedCurrView.value, value)" />
            </el-tooltip>
          </div>
        </div>
        <div class="mx-4 left-divider"></div>
        <div class="f-c-c">
          <div>
            <el-tooltip content="路由模式" placement="bottom">
              <el-select
                @change="SERVICE.changeLinkRouterConfig(DATA.linkRouterConfig, paper)"
                v-model="DATA.linkRouterConfig.value.name"
                placeholder="请选择路由模式">
                <el-option
                  v-for="item in DATA.linkRouterOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-tooltip>
          </div>
          <div class="ml-2">
            <el-tooltip content="连接端样式" placement="bottom">
              <el-select
                @change="SERVICE.changeConnectorConfig(DATA.linkConnectorConfig, paper)"
                v-model="DATA.linkConnectorConfig.value.name"
                placeholder="请选择连接端样式">
                <el-option
                  v-for="item in DATA.linkConnectorOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-tooltip>
          </div>
        </div>
      </div>
    </div>
    <div class="bleuon__diagraming-wrapper f-c-b">
      <div class="bleuon__diagraming-sidebar relative bg-blue w-15vw h-85vh">
        <el-button @click="SERVICE.addPrimaryRectangle(graph)">基础正方形</el-button>
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

<style scoped lang="scss">
.left-divider {
  height: 20px;
  width: 0;
  border-left: 1px solid #dfe2e5;
}
</style>
