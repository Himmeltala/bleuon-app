<script lang="ts" setup>
/**
 * @description 社区模板详细页
 * @author zheng
 * @since 2023/10/5
 */

import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import { dia } from "jointjs";
import { createJointjs } from "@common/lib/jointjs";
import { DateUtil } from "@common/utils";
import { Requests } from "@common/requests";
import { Services } from "@mainapp/service";
import CommonHeader from "@mainapp/fragments/CommonHeader.vue";

const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();

const route = useRoute();
const mainData = ref<BlueprintFlowchartModel>();
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

async function fetchData(params: BlueprintFlowchartModel) {
  mainData.value = await Requests.Blueprint.findById(params);
}

function replicate() {
  Requests.Blueprint.replicate(mainData.value, token.id);
}

function collect() {
  Requests.Blueprint.addCollecting(mainData.value, token.id);
}

await fetchData({ id: route.params.id.toString() });

const lastView = shallowRef({
  view: null,
  model: null
});
const currView = shallowRef(null);
const activeLink = ref(false);
const activeElem = ref(false);
const scale = ref(1);
const offsetX = ref(0);
const offsetY = ref(0);

onMounted(() => {
  const jointjs = createJointjs({
    el: "jointjs-content",
    width: "85vw",
    height: "75vh",
    gridSize: mainData.value.flowchart.gridSize,
    bgColor: mainData.value.flowchart.bgColor,
    drawGrid: JSON.parse(mainData.value.flowchart.drawGrid),
    defaultConnector: JSON.parse(mainData.value.flowchart.connectorDefault),
    defaultRouter: JSON.parse(mainData.value.flowchart.routerDefault)
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  paper.value.freeze();

  if (mainData.value.flowchart.json) {
    graph.value.fromJSON(JSON.parse(mainData.value.flowchart.json));
  }

  paper.value.unfreeze();

  const events = new Services.Listener.JointJsEventService(
    lastView,
    currView,
    activeLink,
    activeElem,
    scale,
    offsetX,
    offsetY
  );

  paper.value.on({
    "blank:mousewheel": evt => events.onMousewheelBlank(evt, paper.value)
  });

  Requests.Blueprint.upgrade({
    config: {
      ignore200: true
    },
    model: { views: mainData.value.views + 1, id: mainData.value.id }
  });
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
              <div class="font-bold text-1.2rem">{{ mainData.flowchart.filename }}</div>
              <div class="mt-2 text-text-secondary">
                修改:{{ DateUtil.formatted(mainData.flowchart.modifyDate) }}
              </div>
            </div>
            <div class="f-c-s">
              <div class="f-c-s mr-4">
                <div class="i-tabler-eye mr-2"></div>
                <span>{{ mainData.views }}</span>
              </div>
              <div class="mr-4">
                <el-button @click="collect">
                  {{ mainData.stars }}
                  <template #icon>
                    <div class="i-tabler-star"></div>
                  </template>
                </el-button>
              </div>
              <div>
                <el-button type="primary" @click="replicate">
                  导入模板
                  <template #icon>
                    <div class="i-tabler-file-import"></div>
                  </template>
                </el-button>
              </div>
            </div>
          </div>
          <div class="f-c-c">
            <div id="jointjs-content" class="mt-5"></div>
          </div>
        </div>
        <div class="information mt-5 p-5 bg-bg-overlay">
          <div class="font-500 text-1.2rem">描述：{{ mainData.description }}</div>
          <div class="mt-5">
            <el-tag v-for="item in JSON.parse(mainData.tags)" class="mr-5">{{ item }}</el-tag>
          </div>
          <router-link :to="'/u/profile/' + mainData.flowchart.consumer.id">
            <div class="cursor-pointer f-c-e mt-5">
              <img :src="mainData.flowchart.consumer.avatar" class="mr-4 w-10 h-10 rd-50%" />
              作者：{{ mainData.flowchart.consumer.username }}
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
