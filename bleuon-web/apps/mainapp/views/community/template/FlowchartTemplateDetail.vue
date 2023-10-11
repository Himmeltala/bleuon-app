<script lang="ts" setup>
/**
 * @description 社区模板详细页
 * @author zheng
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */
// jointjs css
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

import { DateUtil } from "@common/utils";
import { TemplateCommunityApi } from "@mainapp/apis";
import { dia, initJointJs } from "@mainapp/lib";
import { ListenerService } from "@mainapp/service/diagraming/flowchart";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();

const route = useRoute();
const data = ref<TemplateFlowchartData>();

async function fetchData(params: TemplateFlowchartData) {
  data.value = await TemplateCommunityApi.findById(params);
}

function replicateFlowchart() {
  TemplateCommunityApi.replicate(data.value, res => ElMessage.success(res.message));
}

function collectFlowchart() {
  TemplateCommunityApi.addCollect(data.value);
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

  paper.value.freeze();

  if (data.value.flowchart.json) {
    graph.value.fromJSON(JSON.parse(data.value.flowchart.json));
  }

  paper.value.options.linkConnectorConfig = JSON.parse(data.value.flowchart.connectorDefault);
  paper.value.options.linkRouterConfig = JSON.parse(data.value.flowchart.routerDefault);

  paper.value.unfreeze();

  paper.value.on({
    "blank:mousewheel": evt => ListenerService.onMousewheelBlank(evt, paper.value)
  });

  TemplateCommunityApi.upgrade(
    { views: data.value.views + 1, id: data.value.id },
    { nomessage: true }
  );
});
</script>

<template>
  <div class="template-community slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader></CommonHeader>
    <div class="content f-c-c mb-5">
      <div class="wrapper w-80vw mt-5">
        <div class="bg-bg-overlay p-5">
          <div class="f-c-b">
            <div>
              <div class="font-bold text-1.2rem">{{ data.flowchart.fileName }}</div>
              <div class="f-c-s mt-2 text-text-secondary">
                <div class="i-tabler-clock-edit mr-2"></div>
                <span>
                  {{ DateUtil.formatted(data.flowchart.modifyDate) }}
                </span>
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
                <el-button type="primary" @click="replicateFlowchart">
                  导入模板
                  <template #icon>
                    <div class="i-tabler-file-import"></div>
                  </template>
                </el-button>
              </div>
            </div>
          </div>
          <div class="f-c-c">
            <div id="bleuon-flowchart-content" class="mt-5"></div>
          </div>
        </div>
        <div class="information mt-5 p-5 bg-bg-overlay">
          <div class="font-500 text-1.2rem">描述：{{ data.description }}</div>
          <div class="mt-5">
            <el-tag v-for="item in JSON.parse(data.tags)" class="mr-5">{{ item }}</el-tag>
          </div>
          <router-link :to="'/u/profile/' + data.flowchart.user.id">
            <div class="cursor-pointer f-c-e mt-5">
              <img :src="data.flowchart.user.avatar" class="mr-4 w-10 h-10 rd-50%" />
              作者：{{ data.flowchart.user.username }}
            </div>
          </router-link>
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
