<script lang="ts" setup>
/**
 * @description Flowchat 头部工具
 * @author zheng
 * @since 2023/9/21
 */

import { dia } from "jointjs";
import { ElSelectData } from "@common/data";
import { Services } from "@mainapp/service";

const paper = inject<dia.Paper>(Consts.BLEUON_FLOWCHART_PAPER);

defineProps({
  activeElem: {
    type: Boolean,
    default: false
  },
  activeLink: {
    type: Boolean,
    default: false
  },
  currView: {
    type: Object as PropType<any>
  },
  lastView: {
    type: Object as PropType<any>
  }
});

const connectorConfig = ref({
  name: "normal"
});
const routerConfig = ref({
  name: "normal"
});
const textColorPicker = ref();
const linkColorPicker = ref();
const shapeBgColorPicker = ref();
const fontFamily = ref("微软雅黑");
const textColor = ref("");
const textSize = ref(14);
const gridSize = ref(1);
const shapeStrokeWidth = ref(1.5);
const shapeBorderStyle = ref("solid");
const shapeBackground = ref("white");
</script>

<template>
  <div class="f-c-s bg-bg-primary">
    <div class="tools__text">
      <div class="tools__title">
        <div class="i-tabler-typography mr-1"></div>
        编辑图形文本
      </div>
      <div class="tools__items f-c-c">
        <div class="font-tool">
          <el-tooltip content="字体" placement="bottom">
            <el-select
              v-model="fontFamily"
              :disabled="!activeElem && !activeLink"
              placeholder="请选择字体"
              style="width: 100px"
              @change="value => Services.Cell.changeTextFamily(currView, value)">
              <el-option
                v-for="item in ElSelectData.fontFamilyOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-tooltip>
        </div>
        <div class="bold-tool ml-2">
          <el-tooltip content="加粗" placement="bottom">
            <el-button
              :disabled="!activeElem && !activeLink"
              bg
              text
              @click="Services.Cell.changeTextBold(currView)">
              <template #icon>
                <div class="i-tabler-bold"></div>
              </template>
            </el-button>
          </el-tooltip>
        </div>
        <div class="italic-tool ml-2">
          <el-tooltip content="斜体" placement="bottom">
            <el-button
              :disabled="!activeElem && !activeLink"
              bg
              text
              @click="Services.Cell.changeTextItalic(currView)">
              <template #icon>
                <div class="i-tabler-italic"></div>
              </template>
            </el-button>
          </el-tooltip>
        </div>
        <div class="underline-tool ml-2">
          <el-tooltip content="下划线" placement="bottom">
            <el-button
              :disabled="!activeElem && !activeLink"
              bg
              text
              @click="Services.Cell.changeTextUnderline(currView)">
              <template #icon>
                <div class="i-tabler-underline"></div>
              </template>
            </el-button>
          </el-tooltip>
        </div>
        <div class="font-color-tool ml-2">
          <el-tooltip content="字体颜色" placement="bottom">
            <el-button
              :disabled="!activeElem && !activeLink"
              bg
              text
              @click="Services.Cell.openColorPicker(textColorPicker)">
              <template #icon>
                <div class="i-tabler-text-color"></div>
              </template>
            </el-button>
          </el-tooltip>
          <div class="hidden">
            <el-color-picker
              ref="textColorPicker"
              v-model="textColor"
              @change="value => Services.Cell.changeTextColor(currView, value)" />
          </div>
        </div>
        <div class="font-size-tool ml-2">
          <el-tooltip content="字号(px)" placement="bottom">
            <el-input-number
              v-model="textSize"
              :disabled="!activeElem && !activeLink"
              :max="30"
              :min="14"
              controls-position="right"
              style="width: 85px"
              @keyup.enter="Services.Cell.changeTextSize(currView, textSize)"
              @change="Services.Cell.changeTextSize(currView, textSize)" />
          </el-tooltip>
        </div>
      </div>
    </div>
    <div class="mx-4 left-divider"></div>
    <div class="tools__cellview">
      <div class="tools__title">
        <div class="i-tabler-circles mr-1"></div>
        编辑图形样式
      </div>
      <div class="tools__items f-c-c">
        <div class="border-bold-tool">
          <el-tooltip content="连线粗细" placement="bottom">
            <el-select
              v-model="shapeStrokeWidth"
              :disabled="!activeElem && !activeLink"
              placeholder="请选择连线粗细"
              style="width: 85px"
              @change="value => Services.Cell.changeStrokeWidth(currView, value)">
              <el-option
                v-for="item in ElSelectData.shapeStrokeWidthOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-tooltip>
        </div>
        <div class="border-style-tool ml-2">
          <el-tooltip content="连线样式" placement="bottom">
            <el-select
              v-model="shapeBorderStyle"
              :disabled="!activeElem && !activeLink"
              placeholder="请选择连线样式"
              style="width: 130px"
              @change="value => Services.Cell.changeBorderStyle(currView, value)">
              <el-option label="实线" value="solid">
                <img
                  class="object-cover w-100% h-100%"
                  src="https://www.processon.com/v5_editor/compile/zhixian.ee583027.png" />
              </el-option>
              <el-option label="虚线" value="dashed">
                <img
                  class="object-cover w-100% h-100%"
                  src="https://www.processon.com/v5_editor/compile/xuxian.a1d63600.png" />
              </el-option>
              <el-option label="点线" value="dotted">
                <img
                  class="object-cover w-100% h-100%"
                  src="https://www.processon.com/v5_editor/compile/dianxian.5180ceef.png" />
              </el-option>
              <el-option label="虚线和点线" value="dashed-dotted">
                <img
                  class="object-cover w-100% h-100%"
                  src="https://www.processon.com/v5_editor/compile/dianhuaxian.ab5e6f5b.png" />
              </el-option>
            </el-select>
          </el-tooltip>
        </div>
        <div class="shape-bg-tool ml-2">
          <el-tooltip content="图形背景色" placement="bottom">
            <el-button
              :disabled="!activeElem"
              bg
              text
              @click="Services.Cell.openColorPicker(shapeBgColorPicker)">
              <template #icon>
                <div class="i-tabler-paint"></div>
              </template>
            </el-button>
          </el-tooltip>
          <div class="hidden">
            <el-color-picker
              ref="shapeBgColorPicker"
              v-model="shapeBackground"
              @change="value => Services.Cell.changeBackground(currView, value)" />
          </div>
        </div>
        <div class="link-color-tool ml-2">
          <el-tooltip content="连线颜色" placement="bottom">
            <el-button
              :disabled="!activeElem && !activeLink"
              bg
              text
              @click="Services.Cell.openColorPicker(linkColorPicker)">
              <template #icon>
                <div class="i-tabler-pencil-minus"></div>
              </template>
            </el-button>
          </el-tooltip>
          <div class="hidden">
            <el-color-picker
              ref="linkColorPicker"
              v-model="textColor"
              @change="value => Services.Cell.changeLinkColor(currView, value)" />
          </div>
        </div>
      </div>
    </div>
    <div class="mx-4 left-divider"></div>
    <div class="tools__config">
      <div class="tools__title">
        <div class="i-tabler-world mr-1"></div>
        全局设置
      </div>
      <div class="tools__items f-c-c">
        <div class="router-config-tool">
          <el-tooltip content="路由模式" placement="bottom">
            <el-select
              v-model="routerConfig.name"
              placeholder="请选择路由模式"
              style="width: 90px"
              @change="Services.Cell.changeRouterConfig(routerConfig, paper)">
              <el-option
                v-for="item in ElSelectData.linkRouterOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-tooltip>
        </div>
        <div class="link-config-tool ml-2">
          <el-tooltip content="连接端样式" placement="bottom">
            <el-select
              v-model="connectorConfig.name"
              placeholder="请选择连接端样式"
              style="width: 90px"
              @change="Services.Cell.changeConnectorConfig(connectorConfig, paper)">
              <el-option
                v-for="item in ElSelectData.linkConnectorOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-tooltip>
        </div>
        <div class="gride-size-tool ml-2">
          <el-tooltip content="网格大小" placement="bottom">
            <el-input-number
              v-model="gridSize"
              :max="30"
              :min="1"
              controls-position="right"
              style="width: 85px"
              @keyup.enter="Services.Cell.changeGridSize(gridSize, paper)"
              @change="Services.Cell.changeGridSize(gridSize, paper)" />
          </el-tooltip>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.left-divider {
  height: 50px;
  width: 0;
  --uno: b-l-1 b-l-solid b-border-primary;
}

.tools__title {
  --uno: text-text-secondary mb-2 text-0 0.8rem f-c-s;
}
</style>
