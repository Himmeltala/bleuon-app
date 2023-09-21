<script setup lang="ts">
import { dia } from "@mainapp/lib";
import * as Data from "./data";
import * as Service from "./service";

defineProps({
  paper: {
    type: dia.Paper
  },
  graph: {
    type: dia.Graph
  }
});

const textColorPickerRef = ref();
const linkColorPickerRef = ref();
const shapeBackgroundColorPickerRef = ref();
</script>

<template>
  <div class="f-c-s">
    <div class="tools__text">
      <div class="tools__title">编辑图形文本</div>
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
              @change="value => Service.changeTextColor(Data.clickedCurrView.value, value)"
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
      <div class="tools__title">编辑图形样式</div>
      <div class="tools__items f-c-c">
        <div class="border-bold-tool">
          <el-tooltip content="边框粗细" placement="bottom">
            <el-select
              style="width: 85px"
              @change="value => Service.changeShapeStrokeWidth(Data.clickedCurrView.value, value)"
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
              style="width: 130px"
              @change="value => Service.changeShapeBorderStyle(Data.clickedCurrView.value, value)"
              v-model="Data.shapeBorderStyle.value"
              placeholder="请选择边框样式">
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
            <el-button text bg @click="Service.openColorPicker(shapeBackgroundColorPickerRef)">
              <template #icon>
                <div class="i-tabler-paint"></div>
              </template>
            </el-button>
          </el-tooltip>
          <div class="hidden">
            <el-color-picker
              ref="shapeBackgroundColorPickerRef"
              @change="value => Service.changeShapeBackground(Data.clickedCurrView.value, value)"
              v-model="Data.shapeBackground.value" />
          </div>
        </div>
      </div>
    </div>
    <div class="mx-4 left-divider"></div>
    <div class="tools__config">
      <div class="tools__title">编辑连线样式</div>
      <div class="tools__items f-c-c">
        <div class="link-color-tool ml-2">
          <el-tooltip content="连线颜色" placement="bottom">
            <el-button text bg @click="Service.openColorPicker(linkColorPickerRef)">
              <template #icon>
                <div class="i-tabler-pencil-minus"></div>
              </template>
            </el-button>
          </el-tooltip>
          <div class="hidden">
            <el-color-picker
              ref="linkColorPickerRef"
              @change="value => Service.changeLinkColor(Data.clickedCurrView.value, value)"
              v-model="Data.textColor.value" />
          </div>
        </div>
      </div>
    </div>
    <div class="mx-4 left-divider"></div>
    <div class="tools__config">
      <div class="tools__title">全局设置</div>
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
</template>

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
