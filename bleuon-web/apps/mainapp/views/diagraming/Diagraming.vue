<script setup lang="ts">
import { dia, initJointJs } from "@mainapp/lib";
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import * as Data from "./data";
import * as Service from "./service";

let paper: dia.Paper = null;
let graph: dia.Graph = null;

const textInputElement = ref<HTMLInputElement>();
const textColorPickerRef = ref();
const shapeBackgroundColorPickerRef = ref();

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
      <div class="f-c-s">
        <div class="tools__text">
          <div class="tools__title">文本工具</div>
          <div class="tools__items f-c-c">
            <div class="font-tool">
              <el-tooltip content="字体" placement="bottom">
                <el-select
                  style="width: 100px"
                  @change="value => Service.changeTextFamily(Data.clickedCurrView.value, value)"
                  v-model="Data.fontFamily.value"
                  placeholder="请选择字体">
                  <el-option
                    v-for="item in Data.fontFamilyOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-tooltip>
            </div>
            <div class="bold-tool ml-2">
              <el-tooltip content="加粗" placement="bottom">
                <el-button text bg @click="Service.changeTextBold(Data.clickedCurrView.value)">
                  <template #icon>
                    <div class="i-tabler-bold"></div>
                  </template>
                </el-button>
              </el-tooltip>
            </div>
            <div class="italic-tool ml-2">
              <el-tooltip content="斜体" placement="bottom">
                <el-button text bg @click="Service.changeTextItalic(Data.clickedCurrView.value)">
                  <template #icon>
                    <div class="i-tabler-italic"></div>
                  </template>
                </el-button>
              </el-tooltip>
            </div>
            <div class="underline-tool ml-2">
              <el-tooltip content="下划线" placement="bottom">
                <el-button text bg @click="Service.changeTextUnderline(Data.clickedCurrView.value)">
                  <template #icon>
                    <div class="i-tabler-underline"></div>
                  </template>
                </el-button>
              </el-tooltip>
            </div>
            <div class="font-color-tool ml-2">
              <el-tooltip content="字体颜色" placement="bottom">
                <el-button text bg @click="Service.openColorPicker(textColorPickerRef)">
                  <template #icon>
                    <div class="i-tabler-text-color"></div>
                  </template>
                </el-button>
              </el-tooltip>
              <div class="hidden">
                <el-color-picker
                  ref="textColorPickerRef"
                  @change="
                    value =>
                      Service.changeTextColor(Data.clickedCurrView.value, value, textColorPickerRef)
                  "
                  v-model="Data.textColor.value" />
              </div>
            </div>
            <div class="font-size-tool ml-2">
              <el-tooltip content="字号(px)" placement="bottom">
                <el-input-number
                  style="width: 85px"
                  v-model="Data.textSize.value"
                  :min="14"
                  :max="30"
                  controls-position="right"
                  @change="value => Service.changeTextSize(Data.clickedCurrView.value, value)" />
              </el-tooltip>
            </div>
          </div>
        </div>
        <div class="mx-4 left-divider"></div>
        <div class="tools__cellview">
          <div class="tools__title">图形工具</div>
          <div class="tools__items f-c-c">
            <div class="border-bold-tool">
              <el-tooltip content="边框粗细" placement="bottom">
                <el-select
                  style="width: 85px"
                  @change="
                    value => Service.changeShapeStrokeWidth(Data.clickedCurrView.value, value)
                  "
                  v-model="Data.shapeStrokeWidth.value"
                  placeholder="请选择边框粗细">
                  <el-option
                    v-for="item in Data.shapeStrokeWidthOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-tooltip>
            </div>
            <div class="border-style-tool ml-2">
              <el-tooltip content="边框样式" placement="bottom">
                <el-select
                  style="width: 85px"
                  @change="
                    value => Service.changeShapeStrokeWidth(Data.clickedCurrView.value, value)
                  "
                  v-model="Data.shapeStrokeWidth.value"
                  placeholder="请选择边框样式">
                  <el-option
                    v-for="item in Data.shapeStrokeWidthOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-tooltip>
            </div>
            <div class="shape-bg-tool ml-2">
              <el-tooltip content="图形背景色" placement="bottom">
                <el-button text bg @click="Service.openColorPicker(shapeBackgroundColorPickerRef)">
                  <template #icon>
                    <div class="i-tabler-paint"></div>
                  </template>
                </el-button>
              </el-tooltip>
              <div class="hidden">
                <el-color-picker
                  ref="shapeBackgroundColorPickerRef"
                  @change="
                    value => Service.changeShapeBackground(Data.clickedCurrView.value, value)
                  "
                  v-model="Data.shapeBackground.value" />
              </div>
            </div>
          </div>
        </div>
        <div class="mx-4 left-divider"></div>
        <div class="tools__config">
          <div class="tools__title">全局配置</div>
          <div class="tools__items f-c-c">
            <div class="router-config-tool">
              <el-tooltip content="路由模式" placement="bottom">
                <el-select
                  style="width: 85px"
                  @change="Service.changeLinkRouterConfig(Data.linkRouterConfig, paper)"
                  v-model="Data.linkRouterConfig.value.name"
                  placeholder="请选择路由模式">
                  <el-option
                    v-for="item in Data.linkRouterOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-tooltip>
            </div>
            <div class="link-config-tool ml-2">
              <el-tooltip content="连接端样式" placement="bottom">
                <el-select
                  style="width: 85px"
                  @change="Service.changeConnectorConfig(Data.linkConnectorConfig, paper)"
                  v-model="Data.linkConnectorConfig.value.name"
                  placeholder="请选择连接端样式">
                  <el-option
                    v-for="item in Data.linkConnectorOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-tooltip>
            </div>
          </div>
        </div>
      </div>
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
  height: 30px;
  width: 0;
  border-left: 1px solid #dfe2e5;
}

.tools__title {
  --uno: text-gray-700 mb-2;
}
</style>
