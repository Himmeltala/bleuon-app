<script lang="ts" setup>
/**
 * @description 分享的 Flowchart 流程图
 * @author zheng
 * @since 2023/9/30
 */

import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import { dia } from "jointjs";
import { createJointjs } from "@common/lib/jointjs";
import { Services } from "@mainapp/service";
import { Requests } from "@common/requests";
import { DateUtil } from "@common/utils";
import HeaderBottomTools from "@mainapp/fragments/flowchart/HeaderBottomTools.vue";
import HeaderTopTools from "@mainapp/fragments/flowchart/HeaderTopTools.vue";
import FooterTools from "@mainapp/fragments/flowchart/FooterTools.vue";

const route = useRoute();
const router = useRouter();
const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const mainData = ref<FlowchartModel>({});
const texteditor = shallowRef<HTMLInputElement>();

provide(Consts.BLEUON_FLOWCHART_PAPER, paper);
provide(Consts.BLEUON_FLOWCHART_GRAPH, graph);
provide(Consts.BLEUON_FLOWCHART_DATA, mainData);
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

async function fetchData() {
  const id = route.params.id.toString();
  const data = await Requests.Flowchart.findIsShare({ id }, () => {
    router.back();
  });
  mainData.value = data;
}

function remodeling() {
  paper.value.freeze();

  if (mainData.value.json) {
    graph.value.fromJSON(JSON.parse(mainData.value.json));
  }

  paper.value.unfreeze();
}

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
    width: "100vw",
    height: "75vh",
    gridSize: mainData.value.gridSize,
    bgColor: mainData.value.bgColor,
    drawGrid: JSON.parse(mainData.value.drawGrid),
    defaultConnector: JSON.parse(mainData.value.connectorDefault),
    defaultRouter: JSON.parse(mainData.value.routerDefault)
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  remodeling();

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
    "element:pointerclick": view => {
      events.onPointerClickElement(view);
    },
    "element:pointerdblclick": view => {
      events.onPointerDoubleClickElement(view, texteditor.value);
    },
    "link:pointerclick": view => {
      events.onPointerClickLink(view);
    },
    "link:pointerdblclick": view => {
      events.onPointerDoubleClickElement(view, texteditor.value);
    },
    "blank:pointerclick": () => {
      events.onPointerClickBlank();
    },
    "blank:mousewheel": evt => {
      events.onMousewheelBlank(evt, paper.value);
    }
  });
});

const dialogVisible = ref(false);

function replicate() {
  mainData.value.filename = mainData.value.filename;
  Requests.Flowchart.replicate({ ...mainData.value, consumerId: token.id }, res =>
    ElMessage.success(res.message)
  );
}

await fetchData();
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderTopTools :data="mainData" class="mb-4">
        <template #tools>
          <el-tooltip content="分享">
            <div class="hover i-tabler-share mr-4" @click="dialogVisible = !dialogVisible"></div>
          </el-tooltip>
          <el-tooltip v-if="token" content="导入模板">
            <div>
              <div class="hover i-tabler-file-import" @click="replicate"></div>
            </div>
          </el-tooltip>
        </template>
      </HeaderTopTools>
      <HeaderBottomTools
        :curr-view="currView"
        :last-view="lastView"
        :active-elem="activeElem"
        :active-link="activeLink" />
    </div>
    <div class="bleuon__flowchart-wrapper f-c-b">
      <div class="left"></div>
      <div class="right">
        <div id="jointjs-content"></div>
        <FooterTools v-model:scale="scale" class="h-3vh" />
      </div>
      <div class="bleuon__flowchart-extra">
        <input ref="texteditor" class="bleuon__flowchart-input absolute hidden" type="text" />
      </div>
    </div>
    <el-dialog v-model="dialogVisible" title="分享流程图" width="40%">
      <el-form ref="shareFormRef" label-position="right" label-width="100px">
        <el-form-item label="状态">流程图已经公开，点击链接浏览</el-form-item>
        <el-form-item label="链接地址">
          <router-link :to="'/share/flowchart/' + mainData.id">
            <el-link type="primary">
              http://localhost:5173/#/share/flowchart/{{ mainData.id }}
            </el-link>
          </router-link>
        </el-form-item>
        <el-form-item label="截止日期">
          {{ DateUtil.formatted(mainData.deadShareDate) }}
        </el-form-item>
      </el-form>
    </el-dialog>
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
    }
  }
}
</style>
