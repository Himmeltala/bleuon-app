<script lang="ts" setup>
/**
 * @description 社区模板详细页
 * @author 郑人滏 42020306
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */
// jointjs css
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

import { dia, initJointJs } from "@mainapp/lib";

import { TemplateCommunityApi } from "@mainapp/apis";
import CommonHeader from "@mainapp/components/CommonHeader.vue";
import { formatted } from "@common/utils/date";
import { ListenerService } from "@mainapp/service/diagraming/flowchart";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();

const route = useRoute();
const data = ref<TemplateFlowchartData>();

async function fetchData(params: TemplateFlowchartData) {
  data.value = await TemplateCommunityApi.findOne(params);
}

function cloneFlowchart() {
  TemplateCommunityApi.cloneOne(data.value, (res) => ElMessage.success(res.message));
}

function collectFlowchart() {
  TemplateCommunityApi.collectOne(data.value);
}

await fetchData({ id: route.params.id.toString() });

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon-flowchart-content",
    width: "80vw",
    height: "100vh",
    gridSize: data.value.flowchart.gridSize,
    bgColor: data.value.flowchart.bgColor,
    drawGrid: JSON.parse(data.value.flowchart.drawGrid)
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;


  if (data.value.flowchart.json) {
    paper.value.freeze();
    graph.value.fromJSON(JSON.parse(data.value.flowchart.json));
    paper.value.unfreeze();
  }

  paper.value.options.linkConnectorConfig = JSON.parse(data.value.flowchart.connectorDefault);
  paper.value.options.linkRouterConfig = JSON.parse(data.value.flowchart.routerDefault);

  paper.value.on({
    "blank:mousewheel": evt => {
      ListenerService.onMousewheelBlank(evt, paper.value);
    }
  });

  TemplateCommunityApi.updateOne({ views: data.value.views + 1, id: data.value.id });
});
</script>

<template>
  <div class="template-community slim-slider h-100vh flow-auto bg-bg-primary">
    <CommonHeader></CommonHeader>
    <div class="content f-c-c mb-5">
      <div class="wrapper w-80vw mt-5">
        <div class="bg-white p-5">
          <div class="f-c-b">
            <div>
              <div class="font-bold text-1.2rem">{{ data.flowchart.fileName }}</div>
              <div class="f-c-s mt-2 text-text-thirdly">
                <div class="i-tabler-clock-edit mr-2"></div>
                <span>{{ formatted("yyyy-MM-dd HH:mm:ss", data.flowchart.modifyDate) }}</span>
              </div>
            </div>
            <div class="f-c-s">
              <div class="f-c-s mr-4">
                <div class="i-tabler-eye mr-2"></div>
                <span>{{ data.views }}</span>
              </div>
              <div class="mr-4">
                <el-button @click="collectFlowchart">
                  {{ data.stars }}
                  <template #icon>
                    <div class="i-tabler-star"></div>
                  </template>
                </el-button>
              </div>
              <div>
                <el-button type="primary" @click="cloneFlowchart">
                  导入模板
                  <template #icon>
                    <div class="i-tabler-file-import"></div>
                  </template>
                </el-button>
              </div>
            </div>
          </div>
          <div class="f-c-c">
            <div class="mt-5" id="bleuon-flowchart-content"></div>
          </div>
        </div>
        <div class="information mt-5 p-5 bg-white">
          <div class="font-500 text-1.1rem">{{ data.description }}</div>
          <div class="mt-5">
            <el-tag class="mr-5" v-for="item in JSON.parse(data.tags)">{{ item }}</el-tag>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
.joint-element {
  .joint-port {
    circle {
      fill: transparent;
      stroke: transparent;
      stroke-width: 0;
      stroke-dasharray: none;
      --uno: transition-all-300;
    }
  }

  &:hover {
    .joint-port {
      circle {
        fill: white !important;
        stroke: black;
        stroke-width: 1px;
        stroke-dasharray: none;
      }
    }
  }
}

.active [joint-selector="line"] {
  stroke: #4666e5;
}
</style>

<style lang="scss" scoped></style>
